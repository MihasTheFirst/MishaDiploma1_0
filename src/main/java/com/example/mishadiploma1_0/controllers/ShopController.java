package com.example.mishadiploma1_0.controllers;

import com.example.mishadiploma1_0.entity.Shop;
import com.example.mishadiploma1_0.serviceces.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ShopController {

  @Autowired
  private ShopService shopService;

  @GetMapping("/order/shop")
  public String supplierPage(Model model) {
    model.addAttribute("title", "магазин");
    return "shop/shop_main_page";
  }

  @GetMapping("/order/shop/new")
  public String addNewSupplier(Model model) {
    return "shop/shop_add";
  }

  @PostMapping("/order/shop/new")
  public String addNewSupplier(Model model,
                               @RequestParam String name,
                               @RequestParam String address) {
    Shop shop = shopService.addNewShop(name, address);
    return "redirect:/order/shop/all";
  }

  @GetMapping("/order/shop/{id}/edit")
  public String editTheExistingSupplier(Model model,
                                        @PathVariable("id") Long id) {
    Shop shop = shopService.getShop(id);
    model.addAttribute("shop", shop);
    return "shop/shop_edit";
  }

  @PostMapping("/order/shop/{id}/edit")
  public String editTheExistingSupplier(Model model,
                                        @PathVariable("id") Long id,
                                        @RequestParam String name,
                                        @RequestParam String address) {
    Shop shop = shopService.updateExistingShop(id, name, address);
    return "redirect:/order/shop/all";
  }

  @GetMapping("/order/shop/{id}")
  public String getTheSupplier(Model model,
                               @PathVariable("id") Long id) {
    Shop shop = shopService.getShop(id);
    model.addAttribute("shop", shop);
    return "shop/shop_details";
  }

  @GetMapping("/order/shop/all")
  public String getAllSuppliers(Model model) {
    Iterable<Shop> shops = shopService.getAllShops();
    model.addAttribute("shops", shops);
    return "shop/shop_details";
  }

  @GetMapping("/order/shop/{id}/delete")
  public String deleteTheSupplier(Model model, @PathVariable("id") Long id) {
    shopService.deleteShop(id);
    return "redirect:/order/shop/all";
  }


}
