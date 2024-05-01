package com.example.mishadiploma1_0.serviceces;

import com.example.mishadiploma1_0.entity.Product;
import com.example.mishadiploma1_0.entity.Supplier;
import com.example.mishadiploma1_0.entity.Supply;
import com.example.mishadiploma1_0.repositories.SupplierRepository;
import com.example.mishadiploma1_0.repositories.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SupplyService {

  @Autowired
  private SupplyRepository supplyRepository;

  public Supply addNewSupply(Supplier supplier, List<Product> products) {
    Supply supply = Supply.builder()
            .supplier(supplier)
            .products(products)
            .build();

    return supplyRepository.save(supply);
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
