package com.example.mishadiploma1_0.controllers;

import com.example.mishadiploma1_0.entity.Product;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StorageController {

  @GetMapping("/storage")
  public String storagePage(Model model) {
    model.addAttribute("title", "склад");
    return "storage/storage_main_page";
  }

  // TODO: check how to pass the param like Product
  // When the supply comes, save products
  @PostMapping("/storage/add")
  public String saveProducts(Model model,
                             @RequestParam List<Product> product) {
    return null;
  }

  // Edit the existing product
  @PutMapping("/storage/{id}/edit")
  public String editExistingProduct(Model midel,
                                    @RequestParam("id") Long id,
                                    @RequestBody Product product) {
    return null;
  }

  // Get all products that are present in BD
  @GetMapping("/storage/products")
  public String getAllProducts(Model model) {
    return null;
  }

  // Delete the product
  @DeleteMapping("/storage/delete")
  public String deleteProducts(Model model,
                               @RequestParam List<Product> product) {
    return null;
  }

}
