package com.sparkequation.spring.trial.api.service;

import com.sparkequation.spring.trial.api.model.Brand;
import com.sparkequation.spring.trial.api.model.Category;
import com.sparkequation.spring.trial.api.model.Product;
import com.sparkequation.spring.trial.api.report.ResponseReport;
import com.sparkequation.spring.trial.api.repository.BrandRepository;
import com.sparkequation.spring.trial.api.repository.CategoryRepository;
import com.sparkequation.spring.trial.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private BrandRepository brandRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, BrandRepository brandRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
    }


    /**
     * Method return all products with dependencies
     *
     * @return List of products
     */
    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }


    /**
     * Save Product. Check outer product before save.
     * Create new Product Or update product in database
     *
     * @param outProduct Product Object from outer source
     * @return Response report with errors or success
     */
    @Override
    public ResponseEntity<ResponseReport> saveProduct(Product outProduct) {
        ResponseReport responseReport = new ResponseReport();

        String check = checkBeforeSaveProduct(outProduct);

        if (check != null) {
            responseReport.addError(outProduct.getName(), check);
            return new ResponseEntity<>(responseReport, HttpStatus.BAD_REQUEST);
        }

        Product product = productRepository.findByName(outProduct.getName());

        //If cant find such product in database we'll create new product
        if (product == null) {
            product = new Product();
            product.setName(outProduct.getName());
        }

        product.setFeatured(outProduct.isFeatured());
        product.setExpirationDate(outProduct.getExpirationDate());
        product.setItemsInStock(outProduct.getItemsInStock());
        product.setReceiptDate(outProduct.getReceiptDate());
        product.setRating(outProduct.getRating());

        // If a product rating is greater than 8 it must automatically become “featured” product.
        if (outProduct.getRating() > 8) {
            product.setFeatured(true);
        }

        //Find Brand and Categories from database
        Brand brand = brandRepository.findByName(outProduct.getBrand().getName());
        product.setBrand(brand);

        Set<Category> categorySet = new HashSet<>();
        for (Category outCategory : outProduct.getCategories()) {
            categorySet.add(categoryRepository.findByName(outCategory.getName()));
        }
        product.setCategories(categorySet);

        try {
            productRepository.save(product);
            responseReport.addSuccess(product.getName(), "OK");
            return ResponseEntity.ok(responseReport);
        } catch (DataAccessException e) {
            responseReport.addError(product.getName(), "Error while saving in database");
            return new ResponseEntity<>(responseReport, HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Delete product. Find product in database by name and delete it from database.
     *
     * @param name Name of product
     * @return Response report with errors or success
     */
    @Override
    public ResponseEntity<ResponseReport> deleteProduct(String name) {
        ResponseReport responseReport = new ResponseReport();
        Product product = productRepository.findByName(name);

        if (product == null) {
            responseReport.addError(name, "This product does not exist");
            return new ResponseEntity<>(responseReport, HttpStatus.BAD_REQUEST);
        }

        try {
            productRepository.delete(product);
            responseReport.addSuccess(product.getName(), "Deleted");
            return ResponseEntity.ok(responseReport);
        } catch (DataAccessException e) {
            responseReport.addError(product.getName(), "Error while deleting from database");
            return new ResponseEntity<>(responseReport, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Check outer product before save (create or update)
     *
     * @param outProduct Product Object from outer source
     * @return Message of error Or NULL
     */
    private String checkBeforeSaveProduct(Product outProduct) {

        if (ObjectUtils.isEmpty(outProduct.getCategories())
                || outProduct.getCategories().size() > 5
                || outProduct.getCategories().size() < 1) {
            return "Incorrect number of categories";
        }

        if (outProduct.getBrand() == null
                || brandRepository.findByName(outProduct.getBrand().getName()) == null) {
            return "There are no such Brand in database. Select another brand";
        }

        for (Category outCategory : outProduct.getCategories()) {
            if (categoryRepository.findByName(outCategory.getName()) == null) {
                return "There are no such Category " + outCategory.getName() +
                        " in database. Select another category";
            }
        }

        if (outProduct.getExpirationDate() != null) {

            //If a product has an expiration date it must expire not less than 30 days since now.
            if (outProduct.getExpirationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                    .isAfter(LocalDateTime.now().plusDays(30))) {
                return "The expiration date must expire not less than 30 days since now.";
            }
        }

        return null;
    }

}

