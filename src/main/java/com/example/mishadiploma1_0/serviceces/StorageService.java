package com.example.mishadiploma1_0.serviceces;

import com.example.mishadiploma1_0.entity.Product;
import com.example.mishadiploma1_0.repositories.ProductRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageService {
    @Autowired
    private ProductRepository storageRepository;
    public Iterable<Product> getAllProducts() {
        return storageRepository.findAll();
    }

    public Iterable<Product> getAllProductsByName(List<String> names) {
        return storageRepository.findAllByName(names);
    }

}
