package com.example.webappdemo.service;

import com.example.webappdemo.entity.Product;


import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    Product findProductAndAddToCart(Long productId);
}
