package com.example.mishadiploma1_0.serviceces;

import com.example.mishadiploma1_0.entity.Measure;
import com.example.mishadiploma1_0.entity.Product;
import com.example.mishadiploma1_0.entity.ProductPerOrder;
import com.example.mishadiploma1_0.repositories.ProductPerOrderRepository;
import com.example.mishadiploma1_0.repositories.ProductRepository;
import com.example.mishadiploma1_0.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private ProductPerOrderRepository productPerOrderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShopRepository shopRepository;

    public List<ProductPerOrder> createNewOrder(Long idOfShop,
                                                List<String> name,
                                                List<BigDecimal> price,
                                                List<Long> quantity,
                                                List<Measure> measures) {
        return null;
    }
}
