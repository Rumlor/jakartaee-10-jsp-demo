package com.example.webappdemo.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductModel {
    private Long ProductId;
    private Integer productCount;
    private String name;
    private String category;
    private BigDecimal price;
}
