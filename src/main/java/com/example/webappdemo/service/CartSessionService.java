package com.example.webappdemo.service;

import com.example.webappdemo.model.CartModel;
import com.example.webappdemo.model.ProductModel;
import jakarta.ejb.Stateless;

import java.io.Serializable;
import java.util.Optional;



@Stateless
public class CartSessionService implements Serializable {
    private String cartSessionAttr = "cart";
    private SessionAttributeWrapper<CartModel> cartModelSessionAttributeWrapper;


    public void setCartModelSessionAttributeWrapper(SessionAttributeWrapper<CartModel> cartModelSessionAttributeWrapper) {
        this.cartModelSessionAttributeWrapper = cartModelSessionAttributeWrapper;
    }

    public  CartModel getCartModelFromSessionOrCreateNew(){
        return Optional.ofNullable(cartModelSessionAttributeWrapper.getSessionAttribute(cartSessionAttr)).orElseGet(()->{
            CartModel cartModel = CartModel.builder().build();
            this.setCartSessionAttribute(cartModel);
            return cartModel;
        });
    }
    public void addProductToCartSession(ProductModel productModel){
        Optional.ofNullable(cartModelSessionAttributeWrapper.getSessionAttribute(cartSessionAttr)).ifPresent(cartModel -> cartModel.addToCart(productModel));
    }
    public void setCartSessionAttribute(CartModel cartSession){
        cartModelSessionAttributeWrapper.setSessionAttribute(cartSessionAttr,cartSession);
    }
    public void removeProductFromSession(ProductModel productModel){
        cartModelSessionAttributeWrapper.getSessionAttribute(cartSessionAttr).getProductModelList().remove(productModel);
    }
}
