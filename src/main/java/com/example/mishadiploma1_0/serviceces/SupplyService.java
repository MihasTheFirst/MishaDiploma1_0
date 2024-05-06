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
    Supplier supplier = this.findSupplierById(idOfSupplier);

    Supply supply = Supply.builder()
                          .supplier(supplier)
                          .date(LocalDateTime.now())
                          .build();

    Supply savedSupply = supplyRepository.save(supply);

    List<ProductPerSupply> products = this.getListOfProducts(savedSupply, name, price, quantity);
    savedSupply.setProducts(products);

    supplyRepository.save(savedSupply);

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
    Supply supplyFromDb = this.getSupply(id);

    List<ProductPerSupply> savedProductPerSupply = new ArrayList<>(supplyFromDb.getProducts());

    Supplier supplier = this.findSupplierById(idOfSupplier);
    supplyFromDb.setSupplier(supplier);

    try {
      productPerSupplyRepository.deleteBySupplyId(supplyFromDb.getId());
    } catch (Exception ex) { }

    supplyRepository.save(supplyFromDb);

    List<ProductPerSupply> products = this.getListOfProducts(supplyFromDb,
        savedProductPerSupply.stream().map(ProductPerSupply::getName).collect(
        Collectors.toList()),
        price,
        quantity);
    supplyFromDb.setProducts(products);

    supplyRepository.save(supplyFromDb);

    this.updateQuantityOfProduct(savedProductPerSupply, products);
  }

  public void deleteSupply(Long id) {
    Supply supply = this.getSupply(id);

    List<ProductPerSupply> savedProductPerSupplies = new ArrayList<>(supply.getProducts());

    this.updateQuantityOfProduct(savedProductPerSupplies);

    try {
      productPerSupplyRepository.deleteBySupplyId(supply.getId());
    } catch (Exception ex) { }

    supplyRepository.deleteById(supply.getId());
  }

  private Supplier findSupplierById(Long idOfSupplier) {
    return supplierRepository.findById(idOfSupplier)
                             .orElseThrow(NoSuchElementException::new);
  }

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

  private void updateQuantityOfProduct(List<ProductPerSupply> products) {
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

  private List<Product> saveProductsWhichWereDeliveredBySupply(List<ProductPerSupply> supplyList) {
    List<Product> products = new ArrayList<>();

    for (int i = 0; i < supplyList.size(); i++) {
      ProductPerSupply productPerSupply = supplyList.get(i);
      Optional<Product> product = productRepository.updateAmountOfProductIfExists(productPerSupply.getAmount(),
                                                                                  productPerSupply.getName(),
                                                                                  productPerSupply.getPricePerOne());
      if (product.isPresent()) {
        products.add(product.get());
      } else {
        product = Optional.of(Product.builder()
            .name(productPerSupply.getName())
            .pricePerOne(productPerSupply.getPricePerOne())
            .amount(productPerSupply.getAmount())
            .build());

        productRepository.save(product.get());

        products.add(product.get());
      }
    }
    return products;
  }

}
