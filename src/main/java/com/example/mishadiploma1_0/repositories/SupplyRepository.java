package com.example.mishadiploma1_0.repositories;

import com.example.mishadiploma1_0.entity.Supply;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends CrudRepository<Supply, Long> {

    @Query(value = """
    UPDATE supply 
    SET supplier_id = :supplier_id 
    WHERE id = :id
    """, nativeQuery = true)
    void attachSupplierToSupply(@Param("id") Long id,
                                @Param("supplier_id") Long idOfSupplier);
}
