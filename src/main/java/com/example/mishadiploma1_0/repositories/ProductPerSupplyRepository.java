package com.example.mishadiploma1_0.repositories;

import com.example.mishadiploma1_0.entity.Product;
import com.example.mishadiploma1_0.entity.ProductPerSupply;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPerSupplyRepository extends CrudRepository<ProductPerSupply, Long> {

  @Query(value = """
    DELETE FROM product_per_supply WHERE supply_id = :supply_id
  """, nativeQuery = true)
  void deleteBySupplyId(@Param("supply_id") Long id);
}
