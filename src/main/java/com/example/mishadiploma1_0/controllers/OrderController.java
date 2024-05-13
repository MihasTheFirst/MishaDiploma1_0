package com.example.mishadiploma1_0.controllers;

import com.example.mishadiploma1_0.entity.Measure;
import com.example.mishadiploma1_0.entity.Order;
import com.example.mishadiploma1_0.entity.Product;
import com.example.mishadiploma1_0.entity.Supply;
import com.example.mishadiploma1_0.serviceces.OrderService;
import com.example.mishadiploma1_0.serviceces.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class OrderController {

  @Autowired
  private OrderService orderService;
  @Autowired
  private StorageService storageService;

  @GetMapping("/order")
  public String storagePage(Model model) {
    model.addAttribute("title", "замовлення");
    return "order/order_main_page";
  }

  @GetMapping("/order/new")
  public String createNewOrderPage(Model model) {
    Iterable<Product> products = storageService.getAllProducts();
    model.addAttribute("products", products);
    return "order/order_add";
  }

  @PostMapping("/order/new")
  public String createNewOrder(Model model,
                               @RequestParam Long idOfShop,
                               @RequestParam List<String> name,
                               @RequestParam List<BigDecimal> price,
                               @RequestParam List<Long> quantity,
                               @RequestParam List<Measure> measures) {
    orderService.createNewOrder(idOfShop, name, price, quantity, measures);
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
