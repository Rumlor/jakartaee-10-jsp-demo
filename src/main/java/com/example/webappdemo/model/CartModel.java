package com.example.webappdemo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartModel {
    private List<ProductModel> productModelList = new ArrayList<>();

    public boolean isEmpty(){
        return productModelList == null || productModelList.isEmpty();
    }
    public void addToCart(ProductModel productModel){
        this.productModelList.add(productModel);
    }
}

