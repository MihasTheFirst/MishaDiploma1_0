package com.example.mishadiploma1_0.serviceces;

import com.example.mishadiploma1_0.entity.Product;
import com.example.mishadiploma1_0.entity.Supplier;
import com.example.mishadiploma1_0.entity.Supply;
import com.example.mishadiploma1_0.repositories.ProductRepository;
import com.example.mishadiploma1_0.repositories.SupplierRepository;
import com.example.mishadiploma1_0.repositories.SupplyRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Transactional
  public void addNewSupply(Long idOfSupplier,
                             List<String> name,
                             List<BigDecimal> price,
                             List<Long> quantity) {
    Supplier supplier = this.findSupplierById(idOfSupplier);

    List<Product> products = this.getProductsWhichWereDeliveredBySupply(name, price,
        quantity);

    Supply supply = Supply.builder()
                          .supplier(supplier)
                          .products(products)
                          .date(LocalDateTime.now())
                          .build();

     supplyRepository.save(supply);
  }

  public Supply getSupply(Long id) {
    return supplyRepository.findById(id).orElseThrow(NoSuchElementException::new);
  }

  public Iterable<Supply> getAllSupplies() {
    return supplyRepository.findAll();
  }

  public Supply updateExistingSupply(Long id, Long idOfSupplier, List<String> name, List<BigDecimal> price,
      List<Long> quantity) {
    Supply supply = this.getSupply(id);

    Supplier supplier = this.findSupplierById(idOfSupplier);
    List<Product> products = this.getProductsWhichWereDeliveredBySupply(name, price, quantity);
    supply.setSupplier(supplier);
    supply.setProducts(products);
    return supplyRepository.save(supply);
  }

  public void deleteSupply(Long id) {
    Supply supply = this.getSupply(id);
    supplyRepository.delete(supply);
  }

  private Supplier findSupplierById(Long idOfSupplier) {
    return supplierRepository.findById(idOfSupplier)
                             .orElseThrow(NoSuchElementException::new);
  }

  private List<Product> getProductsWhichWereDeliveredBySupply(List<String> name, List<BigDecimal> price,
      List<Long> quantity) {
    List<Product> products = new ArrayList<>();

    int size = name.size();
    for (int i = 0; i < size; i++) {
      Optional<Product> product = productRepository.updateAmountOfProductIfExists(quantity.get(i),
          name.get(i),
          price.get(i));
      if (product.isPresent()) {
        products.add(product.get());
      } else {
        product = Optional.of(Product.builder()
            .name(name.get(i))
            .pricePerOne(price.get(i))
            .amount(quantity.get(i))
            .build());

        productRepository.save(product.get());

        products.add(product.get());
      }
    }
    return products;
  }

}
