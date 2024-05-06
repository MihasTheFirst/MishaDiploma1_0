package com.example.mishadiploma1_0.repositories;

import com.example.mishadiploma1_0.entity.Product;
import com.example.mishadiploma1_0.entity.Storage;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

  @Query(value = """
      UPDATE product SET amount = amount + :amount
      WHERE (CASE
             WHEN name = :name AND
                  price_per_one = :price_per_one THEN 1
             ELSE 0
      END) = 1
      RETURNING id, name, amount, price_per_one;
  """, nativeQuery = true)
  Optional<Product> updateAmountOfProductIfExists(@Param("amount") Long amount,
                                                  @Param("name") String name,
                                                  @Param("price_per_one") BigDecimal price);

}
