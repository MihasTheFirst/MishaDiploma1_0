package com.example.mishadiploma1_0.controllers;

import com.example.mishadiploma1_0.entity.Measure;
import com.example.mishadiploma1_0.entity.Product;
import com.example.mishadiploma1_0.entity.Supplier;
import com.example.mishadiploma1_0.entity.Supply;
import com.example.mishadiploma1_0.serviceces.StorageService;
import com.example.mishadiploma1_0.serviceces.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class StorageController {

    @Autowired
    private StorageService StorageService;


    @GetMapping("/storage")
    public String getTheSupplier(Model model) {
        return "/storage/storage_main_page";
    }

    @GetMapping("/storage/all")
    public String getAllProducts(Model model) {
        Iterable<Product> products = StorageService.getAllProducts();
        model.addAttribute("products", products);
        return "storage/storage_details";
    }

}

