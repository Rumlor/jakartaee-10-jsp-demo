package com.example.webappdemo.service;

import com.example.webappdemo.model.CartModel;
import com.example.webappdemo.model.ProductModel;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;


public class CartSessionService {
    private final String cartSessionAttr;
    private final SessionAttributeWrapper<CartModel> cartModelSessionAttributeWrapper;

    public CartSessionService(String cartSessionAttr, SessionAttributeWrapper<CartModel> cartModelSessionAttributeWrapper) {
        this.cartSessionAttr = cartSessionAttr;
        this.cartModelSessionAttributeWrapper = cartModelSessionAttributeWrapper;
    }

    public  CartModel getCartModelFromSessionOrCreateNew(){
        return Optional.ofNullable(cartModelSessionAttributeWrapper.getSessionAttribute(cartSessionAttr)).orElse(CartModel.builder().build());
    }
    public void addProductToCartSession(ProductModel productModel){
        Optional.ofNullable(cartModelSessionAttributeWrapper.getSessionAttribute(cartSessionAttr)).ifPresent(cartModel -> cartModel.addToCart(productModel));
    }
    public void setCartSession(CartModel cartSession){
        cartModelSessionAttributeWrapper.setSessionAttribute(cartSessionAttr,cartSession);
    }
    public void removeProductFromSession(ProductModel productModel){
        cartModelSessionAttributeWrapper.getSessionAttribute(cartSessionAttr).getProductModelList().remove(productModel);
    }
}
