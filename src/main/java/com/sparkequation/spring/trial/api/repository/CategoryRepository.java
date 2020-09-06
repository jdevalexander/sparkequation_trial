package com.sparkequation.spring.trial.api.repository;

import com.sparkequation.spring.trial.api.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByName(String name);

}
