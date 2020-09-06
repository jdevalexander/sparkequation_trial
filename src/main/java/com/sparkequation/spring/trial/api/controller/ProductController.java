package com.sparkequation.spring.trial.api.controller;

import com.sparkequation.spring.trial.api.model.Product;
import com.sparkequation.spring.trial.api.report.ResponseReport;
import com.sparkequation.spring.trial.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/getProducts", method = RequestMethod.GET)
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
    public ResponseEntity<ResponseReport> saveProduct(
            @RequestBody Product product
    ) {
        return productService.saveProduct(product);
    }

    @RequestMapping(value = "/deleteProduct", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseReport> deleteProducts(
            @RequestParam String name
    ) {
        return productService.deleteProduct(name);
    }

}
