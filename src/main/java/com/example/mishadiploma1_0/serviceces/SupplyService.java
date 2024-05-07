package com.example.mishadiploma1_0.serviceces;

import com.example.mishadiploma1_0.entity.Product;
import com.example.mishadiploma1_0.entity.ProductPerSupply;
import com.example.mishadiploma1_0.entity.Supplier;
import com.example.mishadiploma1_0.entity.Supply;
import com.example.mishadiploma1_0.repositories.ProductPerSupplyRepository;
import com.example.mishadiploma1_0.repositories.ProductRepository;
import com.example.mishadiploma1_0.repositories.SupplierRepository;
import com.example.mishadiploma1_0.repositories.SupplyRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SupplyService {

  @Autowired
  private SupplyRepository supplyRepository;
  @Autowired
  private SupplierRepository supplierRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  ProductPerSupplyRepository productPerSupplyRepository;

  @Transactional
  public void addNewSupply(Long idOfSupplier,
                           List<String> name,
                           List<BigDecimal> price,
                           List<Long> quantity) {
    // find Suplier by id
    Supplier supplier = this.findSupplierById(idOfSupplier);

    // create new Supply
    Supply supply = Supply.builder()
                          .supplier(supplier)
                          .date(LocalDateTime.now())
                          .build();

    // save the Supply in db
    Supply savedSupply = supplyRepository.save(supply);

    // create list ProductPerSupply from names, prices, quantities
    List<ProductPerSupply> products = this.getListOfProducts(savedSupply, name, price, quantity);
    savedSupply.setProducts(products);

    // updaete Supply with set ProductPerSupply
    supplyRepository.save(savedSupply);

    // update values in storage
    this.saveProductsWhichWereDeliveredBySupply(products);
  }

  public Supply getSupply(Long id) {
    return supplyRepository.findById(id).orElseThrow(NoSuchElementException::new);
  }

  public Iterable<Supply> getAllSupplies() {
    return supplyRepository.fetchAllSupplies();
  }

  public void updateExistingSupply(Long id, Long idOfSupplier, List<BigDecimal> price,
      List<Long> quantity) {
    // find Supply by id
    Supply supplyFromDb = this.getSupply(id);

    // list of all products that were attached to the particular Supply -> supplyFromDb
    List<ProductPerSupply> savedProductPerSupply = new ArrayList<>(supplyFromDb.getProducts());

    // find Supplier by id
    Supplier supplier = this.findSupplierById(idOfSupplier);
    supplyFromDb.setSupplier(supplier);

    try {
      // delete supplyFromDb to create it anew
      productPerSupplyRepository.deleteBySupplyId(supplyFromDb.getId());
    } catch (Exception ex) { }

    // create new Supply with new parameters
    supplyRepository.save(supplyFromDb);

    // create updated ProductPerSupply and save it
    List<ProductPerSupply> products = this.getListOfProducts(supplyFromDb,
        savedProductPerSupply.stream().map(ProductPerSupply::getName).collect(
        Collectors.toList()),
        price,
        quantity);
    supplyFromDb.setProducts(products);

    // save updated Supply with new ProductPerSupply
    supplyRepository.save(supplyFromDb);

    // update values in the Storage
    this.updateQuantityOfProduct(savedProductPerSupply, products);
  }

  public void deleteSupply(Long id) {
    // find Suppl by id
    Supply supply = this.getSupply(id);

    // list of all products that were attached to the particular Supply -> supply
    List<ProductPerSupply> savedProductPerSupplies = new ArrayList<>(supply.getProducts());

    // update values in the Storage
    this.updateQuantityOfProductDel(savedProductPerSupplies);

    try {
      // delete ProductPerSupply by id
      productPerSupplyRepository.deleteBySupplyId(supply.getId());
    } catch (Exception ex) { }

    // delete Supply
    supplyRepository.deleteById(supply.getId());
  }

  private Supplier findSupplierById(Long idOfSupplier) {
    return supplierRepository.findById(idOfSupplier)
                             .orElseThrow(NoSuchElementException::new);
  }

  // Update values in the Storage if Supply were updated
  private void updateQuantityOfProduct(List<ProductPerSupply> savedProducts,
                                       List<ProductPerSupply> newProducts) {
    for(int i = 0; i < newProducts.size(); i++) {
      long newAmount = newProducts.get(i).getAmount() - savedProducts.get(i).getAmount();
      this.productRepository.updateAmountAndPriceOfProductIfExists(
          newAmount,
          savedProducts.get(i).getName(),
          savedProducts.get(i).getPricePerOne(),
          newProducts.get(i).getPricePerOne());
    }
  }

  // Update values in the Storage if Supply were deleted
  private void updateQuantityOfProductDel(List<ProductPerSupply> products) {
    for(int i = 0; i < products.size(); i++) {
      productRepository.updateAmountAndPriceOfProductIfExists(
          products.get(i).getAmount() * -1,
          products.get(i).getName(),
          products.get(i).getPricePerOne(),
          products.get(i).getPricePerOne());
    }
    try {
      productRepository.removeEmptySpaces();
    } catch (Exception ex) { }
  }

  // create list of ProductPerSupply from lists of names, prices and quantities
  // that we get from UI
  private List<ProductPerSupply> getListOfProducts(Supply supply,
                                                   List<String> name,
                                                   List<BigDecimal> price,
                                                   List<Long> quantity) {
    List<ProductPerSupply> products = new ArrayList<>();

    int size = name.size();
    for (int i = 0; i < size; i++) {
        ProductPerSupply product = ProductPerSupply.builder()
            .name(name.get(i))
            .pricePerOne(price.get(i))
            .amount(quantity.get(i))
            .supply(supply)
            .build();

        productPerSupplyRepository.save(product);
        products.add(product);
      }

    return products;
  }

  private void saveProductsWhichWereDeliveredBySupply(List<ProductPerSupply> supplyList) {
    for (int i = 0; i < supplyList.size(); i++) {
      ProductPerSupply productPerSupply = supplyList.get(i);
      // check if product exists
      Optional<Product> product = productRepository.updateAmountOfProductIfExists(
          productPerSupply.getAmount(),
          productPerSupply.getName(),
          productPerSupply.getPricePerOne());
      if (!product.isPresent()) {
        // save new product
        product = Optional.of(Product.builder()
            .name(productPerSupply.getName())
            .pricePerOne(productPerSupply.getPricePerOne())
            .amount(productPerSupply.getAmount())
            .build());

        productRepository.save(product.get());

      }
    }
  }

}
