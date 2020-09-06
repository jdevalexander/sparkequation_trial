package com.sparkequation.spring.trial.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "PRODUCT")
public class Product {

    @JsonIgnore
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //This generation doesn't work in my app, so I decided to use another strategy to generate ID
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PRODUCT_SEQ")
//    @SequenceGenerator(name = "PRODUCT_SEQ", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "ISFEATURED")
    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    @Basic
    @Column(name = "EXPIRATIONDATE")
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Basic
    @Column(name = "ITEMSINSTOCK")
    public int getItemsInStock() {
        return itemsInStock;
    }

    public void setItemsInStock(int itemsInStock) {
        this.itemsInStock = itemsInStock;
    }

    @Basic
    @Column(name = "RECEIPTDATE")
    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    @Basic
    @Column(name = "RATING")
    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @ManyToOne(targetEntity = Brand.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "BRANDID", foreignKey = @ForeignKey(name = "fk_brand"))
    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "CATEGORY_PRODUCT",
            joinColumns = {
                @JoinColumn(name = "PRODUCTID", referencedColumnName = "ID",
                    foreignKey = @ForeignKey(name = "fk_r_product_category"))},
            inverseJoinColumns = {
                @JoinColumn(name = "CATEGORYID", referencedColumnName = "ID",
                    foreignKey = @ForeignKey(name = "fk_r_category_product"))
            })
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    private int id;
    private String name;
    private boolean featured;
    private Date expirationDate;
    private int itemsInStock;
    private Date receiptDate;
    private double rating;

    private Brand brand;
    private Set<Category> categories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                featured == product.featured &&
                itemsInStock == product.itemsInStock &&
                Double.compare(product.rating, rating) == 0 &&
                Objects.equals(name, product.name) &&
                Objects.equals(expirationDate, product.expirationDate) &&
                Objects.equals(receiptDate, product.receiptDate) &&
                Objects.equals(brand, product.brand) &&
                Objects.equals(categories, product.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, featured, expirationDate, itemsInStock, receiptDate, rating, brand, categories);
    }
}
