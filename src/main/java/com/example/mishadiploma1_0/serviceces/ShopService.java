package com.example.mishadiploma1_0.serviceces;

import com.example.mishadiploma1_0.entity.Shop;
import com.example.mishadiploma1_0.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    public Shop addNewShop(String name, String address) {
        Shop shop = Shop.builder()
                        .name(name)
                        .address(address)
                        .build();

        return shopRepository.save(shop);
    }

    public Shop getShop(Long id) {
        return shopRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Iterable<Shop> getAllShops() {
        return shopRepository.findAll();
    }

    public Shop updateExistingShop(Long id, String name, String address) {
        Shop shop = this.getShop(id);
        shop.setName(name);
        shop.setAddress(address);
        return shopRepository.save(shop);
    }

    public void deleteShop(Long id) {
        Shop shop = this.getShop(id);
        shopRepository.delete(shop);
    }
}
