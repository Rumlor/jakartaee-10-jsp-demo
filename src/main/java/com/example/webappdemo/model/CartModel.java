package com.example.webappdemo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Builder
public class CartModel {
    @Builder.Default
    private List<ProductModel> productModelList = new ArrayList<>();

    public boolean isEmpty(){
        return productModelList == null || productModelList.isEmpty();
    }
    public void addToCart(ProductModel productModel){
        Optional<ProductModel> productInCart =  productModelList.stream().filter(prdct->prdct.getProductId().equals(productModel.getProductId())).findFirst();
        if(productInCart.isPresent()){
            ProductModel productModelInCart =productInCart.get();
            productModelInCart.setCount(productModelInCart.getCount() + 1);
        }
        else {
            productModel.setCount(1);
            this.productModelList.add(productModel);
        }
    }
}

