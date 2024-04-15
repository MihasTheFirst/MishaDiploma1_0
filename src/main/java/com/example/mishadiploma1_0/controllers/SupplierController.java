package com.example.mishadiploma1_0.controllers;

import com.example.mishadiploma1_0.entity.Supplier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SupplierController {

  @GetMapping("/supply/supplier")
  public String supplierPage(Model model) {
    model.addAttribute("title", "постачальник");
    return "supply/supplier_main_page";
  }

  @PostMapping("/supply/supplier/new")
  public String addNewSupplier(Model model, @RequestBody Supplier supplier) {
    return null;
  }

  @PutMapping("/supply/supplier/{id}/edit")
  public String editTheExistingSupplier(Model model, @RequestParam("id") Long id, @RequestBody Supplier supplier) {
    return null;
  }

  @GetMapping("/supply/supplier/all")
  public String getAllSuppliers(Model model) {
    return null;
  }

  @DeleteMapping("/supply/supplier/{id}/delete")
  public String deleteTheSupplier(Model model, @RequestParam("id") Long id) {
    return null;
  }

}
