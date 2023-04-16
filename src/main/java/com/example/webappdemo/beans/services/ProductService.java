package com.example.webappdemo.beans.services;

import com.example.webappdemo.entity.Product;


import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product findProductAndAddToCart(Long productId);
    void findProductAndDeleteFromCart(Integer count, Long productId);
}
