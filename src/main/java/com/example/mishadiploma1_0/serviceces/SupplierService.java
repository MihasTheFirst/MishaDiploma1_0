package com.example.mishadiploma1_0.serviceces;

import com.example.mishadiploma1_0.entity.Supplier;
import com.example.mishadiploma1_0.repositories.SupplierRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {
  @Autowired
  private SupplierRepository supplierRepository;

  public Supplier addNewSupplier(String name, String contact) {
    Supplier supplier = Supplier.builder()
                                .name(name)
                                .contact(contact)
                                .build();

    return supplierRepository.save(supplier);
  }

  public Supplier getSupplier(Long id) {
    return supplierRepository.findById(id).orElseThrow(NoSuchElementException::new);
  }

  public Iterable<Supplier> getAllSuppliers() {
    return supplierRepository.findAll();
  }

  public Supplier updateExistingSupplier(Long id, String name, String contact) {
    Supplier supplier = this.getSupplier(id);
    supplier.setName(name);
    supplier.setContact(contact);
    return supplierRepository.save(supplier);
  }

  public void deleteSupplier(Long id) {
    Supplier supplier = this.getSupplier(id);
    supplierRepository.delete(supplier);
  }

}
