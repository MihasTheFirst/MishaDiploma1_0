package com.example.mishadiploma1_0.repositories;

import com.example.mishadiploma1_0.entity.Supply;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends CrudRepository<Supply, Long> {

  @Query("""
    SELECT s FROM Supply s JOIN FETCH s.products
  """)
  List<Supply> fetchAllSupplies();

  void deleteById(Long id);
}
