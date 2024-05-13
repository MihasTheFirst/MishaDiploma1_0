package com.example.mishadiploma1_0.serviceces;

import com.example.mishadiploma1_0.entity.*;
import com.example.mishadiploma1_0.repositories.OrderRepository;
import com.example.mishadiploma1_0.repositories.ProductPerOrderRepository;
import com.example.mishadiploma1_0.repositories.ProductRepository;
import com.example.mishadiploma1_0.repositories.ShopRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private ProductPerOrderRepository productPerOrderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShopService shopService;
    @Autowired
    private OrderRepository orderRepository;

    public void createNewOrder(Long idOfShop,
                                                List<String> name,
                                                List<BigDecimal> price,
                                                List<Long> quantity,
                                                List<Measure> measures) {
        // find Shop by id
        Shop shop = shopService.getShop(idOfShop);

        // create new Order
        Order order = Order.builder()
                .shop(shop)
                .date(LocalDateTime.now())
                .build();

        // save Order in db
        Order savedOrder = orderRepository.save(order);

        // create list ProductPerOrderProductPerOrder from names, prices, quantities
        List<ProductPerOrder> products = this.getListOfProducts(order, name, price, quantity, measures);

        savedOrder.setProducts(products);

        // updaete Order with set ProductPerSupply
        orderRepository.save(savedOrder);

        // update values in storage
        this.saveProductsWhichWereTransferredForOrder(products);
    }

    // create list of ProductPerSupply from lists of names, prices and quantities
    // that we get from UI
    private List<ProductPerOrder> getListOfProducts(Order order,
                                                     List<String> name,
                                                     List<BigDecimal> price,
                                                     List<Long> quantity,
                                                     List<Measure> measures) {
        List<ProductPerOrder> products = new ArrayList<>();

        int size = name.size();
        for (int i = 0; i < size; i++) {
            if (quantity.get(i) != 0) {
                ProductPerOrder product = ProductPerOrder.builder()
                        .name(name.get(i))
                        .pricePerOne(price.get(i))
                        .amount(quantity.get(i))
                        .order(order)
                        .measure(measures.get(i))
                        .build();

                productPerOrderRepository.save(product);
                products.add(product);
            }
        }

        return products;
    }

    private void saveProductsWhichWereTransferredForOrder(List<ProductPerOrder> orderList) {
        for (int i = 0; i < orderList.size(); i++) {
            ProductPerOrder productPerSupply = orderList.get(i);
                // remove some products
                productRepository.removeSomeProducts(productPerSupply.getName(),
                                                     productPerSupply.getAmount());

        }
    }

    public Iterable<Order> findAllOfTheOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                              .orElseThrow(NoSuchElementException::new);
    }

    public void updateExistingOrder(Long id, Long idOfShop, List<Long> quantity, List<BigDecimal> price) {
        // find Order by id
        Order orderFromDb = this.getOrderById(id);

        // list of all products that were attached to the particular Order -> orderFromDb
        List<ProductPerOrder> savedProductPerOrder = new ArrayList<>(orderFromDb.getProducts());

        // find Shop by id
        Shop shop = shopService.getShop(idOfShop);
        orderFromDb.setShop(shop);

        try {
            // delete orderFromDb to create it anew
            productPerOrderRepository.deleteByOrderId(orderFromDb.getId());
        } catch (Exception ex) { }

        // create new Supply with new parameters
        orderRepository.save(orderFromDb);

        // create updated ProductPerSupply and save it
        List<ProductPerOrder> products = this.getListOfProducts(orderFromDb,
                                                                savedProductPerOrder.stream()
                                                                                    .map(ProductPerOrder::getName)
                                                                                    .collect(Collectors.toList()),
                                                                price,
                                                                quantity,
                                                                savedProductPerOrder.stream()
                                                                        .map(ProductPerOrder::getMeasure)
                                                                        .collect(Collectors.toList()));
        orderFromDb.setProducts(products);

        // save updated Supply with new ProductPerSupply
        orderRepository.save(orderFromDb);

        // update values in the Storage
        this.updateQuantityOfProduct(savedProductPerOrder, products);
    }

    private void updateQuantityOfProduct(List<ProductPerOrder> savedProducts,
                                         List<ProductPerOrder> newProducts) {
        for(int i = 0; i < newProducts.size(); i++) {
            long newAmount = savedProducts.get(i).getAmount() - newProducts.get(i).getAmount() ;
            this.productRepository.updateAmountAndPriceOfProductIfExists(savedProducts.get(i).getName(), newAmount);
        }
    }

    public void deleteOrder(Long id) {
        // find Order by id
        Order order = this.getOrderById(id);

        // list of all products that were attached to the particular Order -> order
        List<ProductPerOrder> savedProductsPerOrder = new ArrayList<>(order.getProducts());

        // update values in the Storage
        this.updateQuantityOfProductDel(savedProductsPerOrder);

        try {
            // delete ProductPerOrder by id
            productPerOrderRepository.deleteByOrderId(id);
        } catch (Exception ex) { }

        // delete Order
        orderRepository.deleteById(id);
    }

    // Update values in the Storage if Supply were deleted
    private void updateQuantityOfProductDel(List<ProductPerOrder> products) {
        for(int i = 0; i < products.size(); i++) {
            productRepository.updateAmountAndPriceOfProductIfExists(
                    products.get(i).getName(),
                    products.get(i).getAmount());
        }
        try {
            productRepository.removeEmptySpaces();
        } catch (Exception ex) { }
    }

}
