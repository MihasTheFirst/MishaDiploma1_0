package com.example.mishadiploma1_0.controllers;

import com.example.mishadiploma1_0.entity.Supplier;
import com.example.mishadiploma1_0.serviceces.SupplierService;
import com.example.mishadiploma1_0.serviceces.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SupplierController {

  @Autowired
  private SupplierService supplierService;

  @GetMapping("/supply/supplier")
  public String supplierPage(Model model) {
    model.addAttribute("title", "постачальник");
    return "supplier/supplier_main_page";
  }

  @GetMapping("/supply/supplier/new")
  public String addNewSupplier(Model model) {
    return "supplier/supplier_add";
  }

  @PostMapping("/supply/supplier/new")
  public String addNewSupplier(Model model,
                               @RequestParam String name,
                               @RequestParam String contact) {
    Supplier supplier = supplierService.addNewSupplier(name, contact);
    return "redirect:/supply/supplier/" + supplier.getId();
  }

  @GetMapping("/supply/supplier/{id}/edit")
  public String editTheExistingSupplier(Model model,
                                        @PathVariable("id") Long id) {
    Supplier supplier = supplierService.getSupplier(id);
    model.addAttribute("supplier", supplier);
    return "supplier/supplier_edit";
  }

  @PostMapping("/supply/supplier/{id}/edit")
  public String editTheExistingSupplier(Model model,
                                        @PathVariable("id") Long id,
                                        @RequestParam String name,
                                        @RequestParam String contact) {
    Supplier supplier = supplierService.updateExistingSupplier(id, name, contact);
    return "redirect:/supply/supplier/" + supplier.getId();
  }

  @GetMapping("/supply/supplier/{id}")
  public String getTheSupplier(Model model,
                                @PathVariable("id") Long id) {
    Supplier supplier = supplierService.getSupplier(id);
    model.addAttribute("suppliers", supplier);
    return "supplier/supplier_details";
  }

  @GetMapping("/supply/supplier/all")
  public String getAllSuppliers(Model model) {
    Iterable<Supplier> suppliers = supplierService.getAllSuppliers();
    model.addAttribute("suppliers", suppliers);
    return "supplier/supplier_details";
  }

  @GetMapping("/supply/supplier/{id}/delete")
  public String deleteTheSupplier(Model model, @PathVariable("id") Long id) {
    supplierService.deleteSupplier(id);
    return "redirect:/supply/supplier";
  }

}
