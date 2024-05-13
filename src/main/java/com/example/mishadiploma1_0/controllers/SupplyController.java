package com.example.mishadiploma1_0.controllers;

import com.example.mishadiploma1_0.entity.*;
import com.example.mishadiploma1_0.serviceces.SupplyService;
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
  public String createNewSupply(Model model,
                                @RequestParam Long idOfSupplier,
                                @RequestParam List<String> name,
                                @RequestParam List<BigDecimal> price,
                                @RequestParam List<Long> quantity,
                                @RequestParam List<Measure> measures) {
    supplyService.addNewSupply(idOfSupplier, name, price, quantity, measures);
    return "redirect:/supply/all";
  }
  @GetMapping("/supply/all")
  public String getAllSupplies(Model model) {
    Iterable<Supply> supplies = supplyService.getAllSupplies();
    model.addAttribute("supplies", supplies);
    return "supply/supply_details";
  }

  @GetMapping("/supply/{id}/edit")
  public String editTheExistingSupplyPage(@PathVariable Long id,
                                          Model model) {
    Supply supply = supplyService.getSupply(id);
    model.addAttribute("products", supply.getProducts())
        .addAttribute("idOfSupplier", supply.getSupplier().getId())
        .addAttribute("id", supply.getId());
    return "supply/supply_edit";
  }

  @PostMapping("/supply/{id}/edit")
  public String editTheExistingSupply(Model model,
      @PathVariable Long id,
      @RequestParam Long idOfSupplier,
      @RequestParam List<Long> quantity,
      @RequestParam List<BigDecimal> price,
      @RequestParam List<Measure> measures) {
    supplyService.updateExistingSupply(id, idOfSupplier, price, quantity, measures);
    return "redirect:/supply/all";
  }

  @GetMapping("/supply/{id}/delete")
  public String deleteSupply(@PathVariable Long id,
                             Model model) {
    supplyService.deleteSupply(id);
    return "redirect:/supply/all";
  }
}
