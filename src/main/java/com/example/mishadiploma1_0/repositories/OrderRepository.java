package com.example.mishadiploma1_0.repositories;

import com.example.mishadiploma1_0.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

}
