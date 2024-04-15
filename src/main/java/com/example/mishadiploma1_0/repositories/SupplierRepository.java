package com.example.mishadiploma1_0.repositories;

import com.example.mishadiploma1_0.entity.Storage;
import com.example.mishadiploma1_0.entity.Supplier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long> {

}
