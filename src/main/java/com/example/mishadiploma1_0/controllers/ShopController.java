package com.example.mishadiploma1_0.controllers;

import com.example.mishadiploma1_0.entity.Shop;
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
public class ShopController {

  @GetMapping("/order/shop")
  public String storagePage(Model model) {
    model.addAttribute("title", "магазин");
    return "order/shop_main_page";
  }

  @PostMapping("/order/shop/new")
  public String addNewShop(Model model, @RequestBody Shop shop) {
    return null;
  }

  @PutMapping("/order/shop/{id}/edit")
  public String editTheExistingShop(Model model, @RequestParam("id") Long id, @RequestBody Shop shop) {
    return null;
  }

  @GetMapping("/order/shop/all")
  public String getAllShops(Model model) {
    return null;
  }

  @DeleteMapping("/order/shop/{id}/delete")
  public String deleteTheShop(Model model, @RequestParam("id") Long id) {
    return null;
  }

}
