package com.example.mishadiploma1_0.serviceces;

import com.example.mishadiploma1_0.entity.Product;
import com.example.mishadiploma1_0.entity.Supplier;
import com.example.mishadiploma1_0.entity.Supply;
import com.example.mishadiploma1_0.repositories.ProductRepository;
import com.example.mishadiploma1_0.repositories.SupplierRepository;
import com.example.mishadiploma1_0.repositories.SupplyRepository;
import jakarta.transaction.Transactional;
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

  public void addNewSupply(Long idOfSupplier,
                             List<String> name,
                             List<BigDecimal> price,
                             List<Long> quantity) {
    Supplier supplier = supplierRepository.findById(idOfSupplier)
                                          .orElseThrow(NoSuchElementException::new);

    List<Product> products = new ArrayList<>();

    int size = name.size();
    for (int i = 0; i < size; i++) {
      Product product = Product.builder()
                               .name(name.get(i))
                               .pricePerOne(price.get(i))
                               .amount(quantity.get(i))
                               .build();

      productRepository.save(product);

      products.add(product);
    }

    Supply supply = Supply.builder()
//            .supplier(supplier)
            .products(products)
            .build();

    Supply savedSupply = supplyRepository.save(supply);
    supplyRepository.attachSupplierToSupply(savedSupply.getId(), idOfSupplier);
  }

  public Supply getSupply(Long id) {
    return supplyRepository.findById(id).orElseThrow(NoSuchElementException::new);
  }

  public Iterable<Supply> getAllSupplies() {
    return supplyRepository.findAll();
  }

  public Supply updateExistingSupply(Long id, Supplier supplier, List<Product> products) {
    Supply supply = this.getSupply(id);
    supply.setSupplier(supplier);
    supply.setProducts(products);
    return supplyRepository.save(supply);
  }

  public void deleteSupply(Long id) {
    Supply supply = this.getSupply(id);
    supplyRepository.delete(supply);
  }

}
