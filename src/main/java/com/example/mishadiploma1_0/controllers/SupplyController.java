package com.example.mishadiploma1_0.controllers;

import com.example.mishadiploma1_0.entity.Supply;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SupplyController {

  @GetMapping("/supply")
  public String storagePage(Model model) {
    model.addAttribute("title", "поставка");
    return "supply/supply_main_page";
  }

  @PostMapping("/supply/new")
  public String createNewSupply(Model model, @RequestBody Supply supply) {
    // TODO: call StorageController.saveProducts
    return null;
  }

  @GetMapping("/supply/all")
  public String getAllSupplies(Model model) {
    return null;
  }

  @GetMapping("/supply/edit")
  public String editTheExistingSupply(Model model) {
    // Keep in mind that if quantity or the products themselves change,
    // it should be seen in Product db
    return null;
  }

}
