package com.example.webappdemo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductModel implements Serializable {
    private Long productId;
    private String name;
    private String category;
    private BigDecimal price;
    private Integer count;

    public BigDecimal getTotalPrice(){
        return price.multiply(BigDecimal.valueOf(count));
    }
}
