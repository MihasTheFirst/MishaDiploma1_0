package com.example.mishadiploma1_0.serviceces;

import com.example.mishadiploma1_0.entity.Product;
import com.example.mishadiploma1_0.repositories.ProductRepository;

import com.example.mishadiploma1_0.repositories.SupplierRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StorageService {
    @Autowired
    private ProductRepository StorageRepository;
    public Iterable<Product> getAllProducts() {
        return StorageRepository.findAll();
    }


}
