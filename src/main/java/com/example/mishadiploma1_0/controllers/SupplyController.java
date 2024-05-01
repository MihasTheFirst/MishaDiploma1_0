package com.example.mishadiploma1_0.controllers;

import com.example.mishadiploma1_0.entity.Supply;
import com.example.mishadiploma1_0.serviceces.SupplierService;
import com.example.mishadiploma1_0.serviceces.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SupplyController {
  @Autowired
  private SupplyService supplyService;

  @GetMapping("/supply")
  public String storagePage(Model model) {
    model.addAttribute("title", "поставка");
    return "supply/supply_main_page";
  }

  @GetMapping("/supply/new")
  public String createNewSupply(Model model) {
    return "supply/supply_add";
  }

  @PostMapping("/supply/new")
  public String createNewSupply(Model model, @RequestBody Supply supply) {
    supplyService.addNewSupply(supply.getSupplier(), supply.getProducts());
    // TODO: call StorageController.saveProducts
    return "redirect:/";
  }
  @GetMapping("/supply/all")
  public String getAllSupplies(Model model) {
    Iterable<Supply> supplies = supplyService.getAllSupplies();
    model.addAttribute("supplies", supplies);
    return null;
  }

  @GetMapping("/supply/edit")
  public String editTheExistingSupply(Model model) {
    // Keep in mind that if quantity or the products themselves change,
    // it should be seen in Product db
    return null;
  }

}
