package com.example.webappdemo.service;

import com.example.webappdemo.entity.Product;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@Stateless
@Named("productBean")
public class ProductServiceImpl implements ProductService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> getAllProducts() {
        return entityManager.createQuery("select p from Product p", Product.class).getResultList();
    }

    @Override
    public Optional<Product> addProductToCart(Long productId) {
        Product product = entityManager.find(Product.class,productId);
        return Optional.empty();
    }

}
