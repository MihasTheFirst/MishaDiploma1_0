package com.example.mishadiploma1_0.repositories;

import com.example.mishadiploma1_0.entity.ProductPerOrder;
import com.example.mishadiploma1_0.entity.ProductPerSupply;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPerOrderRepository extends CrudRepository<ProductPerOrder, Long> {

  @Query(value = """
    DELETE FROM product_per_order WHERE order_id = :order_id
  """, nativeQuery = true)
  void deleteByOrderId(@Param("order_id") Long id);
}
