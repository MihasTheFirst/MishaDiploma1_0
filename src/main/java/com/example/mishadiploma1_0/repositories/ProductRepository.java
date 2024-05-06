package com.example.mishadiploma1_0.repositories;

import com.example.mishadiploma1_0.entity.Product;
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

  @Query(value = """
      UPDATE product SET amount = amount + :amount,
                         price_per_one = :new_price_per_one
      WHERE (CASE
             WHEN name = :name AND
                  price_per_one = :old_price_per_one THEN 1
             ELSE 0
      END) = 1
      RETURNING id, name, amount, price_per_one;
  """, nativeQuery = true)
  Optional<Product> updateAmountAndPriceOfProductIfExists(@Param("amount") Long amount,
      @Param("name") String name,
      @Param("old_price_per_one") BigDecimal oldPrice,
      @Param("new_price_per_one") BigDecimal newPrice);

  @Query(value = """
      DELETE FROM product WHERE amount = 0
  """, nativeQuery = true)
  void removeEmptySpaces();
}
