package com.example.mishadiploma1_0.controllers;

import com.example.mishadiploma1_0.entity.*;
import com.example.mishadiploma1_0.serviceces.OrderService;
import com.example.mishadiploma1_0.serviceces.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
    return "redirect:/order/all";
  }

  @GetMapping("/order/all")
  public String getAllOrders(Model model) {
    Iterable<Order> orders = orderService.findAllOfTheOrders();
    model.addAttribute("orders", orders);
    return "order/order_details";
  }

  @GetMapping("/order/{id}/edit")
  public String editTheExistingOrderPage(@PathVariable Long id,
                                         Model model) {
    Order order = orderService.getOrderById(id);
    Iterable<Product> productsAmount = storageService.getAllProductsByName(order.getProducts()
                                                                                .stream()
                                                                                .map(ProductPerOrder::getName)
                                                                                .collect(Collectors.toList()));
    model.addAttribute("products", order.getProducts())
         .addAttribute("idOfShop", order.getShop().getId())
         .addAttribute("productsAmount", productsAmount)
         .addAttribute("id", order.getId());
    return "order/order_edit";
  }

  @PostMapping("/order/{id}/edit")
  public String editTheExistingSupply(Model model,
                                      @PathVariable Long id,
                                      @RequestParam Long idOfShop,
                                      @RequestParam List<Long> quantity,
                                      @RequestParam List<BigDecimal> price) {
    orderService.updateExistingOrder(id, idOfShop, quantity, price);
    return "redirect:/order/all";
  }

  @GetMapping("/order/{id}/delete")
  public String deleteSupply(@PathVariable Long id,
                             Model model) {
    orderService.deleteOrder(id);
    return "redirect:/order/all";
  }


}
