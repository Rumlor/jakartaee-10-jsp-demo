package com.example.webappdemo.beans.statefulbeans;

import com.example.webappdemo.model.CartModel;
import com.example.webappdemo.model.ProductModel;

import java.math.BigDecimal;
import java.util.List;

public interface CartOperation {
    void addProductToCart(ProductModel productModel);
    void setProductToCart(ProductModel product);
    void removeProductFromCart(ProductModel productModel);
    BigDecimal getCartTotalPrice();
    void emptyCart();
    CartModel getCart();
    List<ProductModel> getProducts();
    void setError(String msg);
    void setInfo(String msg);
    void resetInfoAndError();
    String getError();
    String getInfo();
    boolean getHasInfo();
    boolean getHasError();
}
