package com.sparkequation.spring.trial.api.repository;

import com.sparkequation.spring.trial.api.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Integer> {

    Brand findByName(String name);
}
