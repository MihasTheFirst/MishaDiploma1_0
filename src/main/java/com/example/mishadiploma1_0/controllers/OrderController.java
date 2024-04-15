package com.example.mishadiploma1_0.controllers;

import com.example.mishadiploma1_0.entity.Order;
import com.example.mishadiploma1_0.entity.Supply;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrderController {

  @GetMapping("/order")
  public String storagePage(Model model) {
    model.addAttribute("title", "замовлення");
    return "order/order_main_page";
  }

  @PostMapping("/order/new")
  public String createNewOrder(Model model, @RequestBody Order order) {
    // TODO: call StorageController.deleteProducts
    return null;
  }

  @GetMapping("/order/all")
  public String getAllOrders(Model model) {
    return null;
  }

  @GetMapping("/order/edit")
  public String editExistingOrder(Model model) {
    // Keep in mind that if quantity or the products themselves change,
    // it should be seen in Product db
    return null;
  }


}
