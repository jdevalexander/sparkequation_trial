package com.sparkequation.spring.trial.api.service;

import com.sparkequation.spring.trial.api.model.Product;
import com.sparkequation.spring.trial.api.report.ResponseReport;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();
    ResponseEntity<ResponseReport> saveProduct(Product product);
    ResponseEntity<ResponseReport> deleteProduct(String name);
}
