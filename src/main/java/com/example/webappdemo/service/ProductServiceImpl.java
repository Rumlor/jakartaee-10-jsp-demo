package com.example.webappdemo.service;

import com.example.webappdemo.entity.Product;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@Stateless
@Named("productBean")
public class ProductServiceImpl implements ProductService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Product> getAllProducts() {
        return entityManager.createQuery("select p from Product p", Product.class).getResultList();
    }

}
