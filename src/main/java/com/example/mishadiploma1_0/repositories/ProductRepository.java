package com.example.mishadiploma1_0.repositories;

import com.example.mishadiploma1_0.entity.Product;
import com.example.mishadiploma1_0.entity.Storage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
