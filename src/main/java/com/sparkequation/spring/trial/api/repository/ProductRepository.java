package com.sparkequation.spring.trial.api.repository;


import com.sparkequation.spring.trial.api.model.Brand;
import com.sparkequation.spring.trial.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.name = :name ")
    Product findByName(@Param("name") String name);



}
