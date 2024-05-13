package com.example.mishadiploma1_0.repositories;

import com.example.mishadiploma1_0.entity.Measure;
import com.example.mishadiploma1_0.entity.Product;
import java.math.BigDecimal;
import java.util.List;
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
      RETURNING id, name, amount, measure, price_per_one;
  """, nativeQuery = true)
  Optional<Product> updateAmountOfProductIfExists(@Param("amount") Long amount,
                                                  @Param("name") String name,
                                                  @Param("price_per_one") BigDecimal price);

  @Query(value = """
      UPDATE product SET amount = amount + :amount,
                         price_per_one = :new_price_per_one,
                         measure = :measure
      WHERE (CASE
             WHEN name = :name AND
                  price_per_one = :old_price_per_one THEN 1
             ELSE 0
      END) = 1
      RETURNING id, name, amount, measure, price_per_one;
  """, nativeQuery = true)
  Optional<Product> updateAmountAndPriceOfProductIfExists(@Param("amount") Long amount,
                                                          @Param("name") String name,
                                                          @Param("old_price_per_one") BigDecimal oldPrice,
                                                          @Param("new_price_per_one") BigDecimal newPrice,
                                                          @Param("measure") String measure);

  @Query(value = """
      DELETE FROM product WHERE amount = 0
  """, nativeQuery = true)
  void removeEmptySpaces();

  @Query(value = """
      UPDATE product SET amount = amount - :amount
      WHERE name = :name
      RETURNING id, name, amount, measure, price_per_one;
  """, nativeQuery = true)
  Optional<Product> removeSomeProducts(@Param("name") String name,
                          @Param("amount") Long amount);

  @Query(value = """
      UPDATE product SET amount = amount + :amount
      WHERE name = :name
      RETURNING id, name, amount, measure, price_per_one;
  """, nativeQuery = true)
  Optional<Product> updateAmountAndPriceOfProductIfExists(@Param("name") String name,
                                                          @Param("amount") Long amount);

  @Query(value = """
  SELECT * FROM product
  WHERE name IN (:names)
  """, nativeQuery = true)
  Iterable<Product> findAllByName(List<String> names);
}
