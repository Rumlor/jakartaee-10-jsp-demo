package com.example.webappdemo.beans.statefulbeans;

import com.example.webappdemo.beans.services.ProductService;
import com.example.webappdemo.model.CartModel;
import com.example.webappdemo.model.ProductModel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class CartBean implements  CartOperation, Serializable,BeanLifeCycle {

    private final CartModel cart;
    private String cartInfoMessage;
    private String cartErrorMessage;
    @EJB
    private ProductService productService;

    public CartBean(){
        cart = CartModel.builder().build();
    }


    @Override
    public void addProductToCart(ProductModel productModel) {
        cart.addToCart(productModel);
    }

    @Override
    public void removeProductFromCart(ProductModel productModel) {
        cart.getProductModelList().remove(productModel);
    }

    @Override
    public void emptyCart() {
        cart.getProductModelList().clear();
    }

    @Override
    public CartModel getCart() {
        return this.cart;
    }

    @Override
    public List<ProductModel> getProducts() {
        return this.cart.getProductModelList();
    }

    @Override
    public void setError(String msg) {
        this.cartErrorMessage = msg;
    }

    @Override
    public void setInfo(String msg) {
        this.cartInfoMessage = msg;
    }

    @Override
    public String getError() {
        return this.cartErrorMessage;
    }

    @Override
    public String getInfo() {
        return this.cartInfoMessage;
    }

    @Override
    public boolean getHasInfo() {
        return this.cartInfoMessage != null && !this.cartInfoMessage.isEmpty();
    }

    @Override
    public boolean getHasError() {
        return this.cartErrorMessage != null && !this.cartErrorMessage.isEmpty();
    }

    @PostConstruct
    public void constructed() {
        System.out.println("constructed");
    }

    @PreDestroy
    public void destroyed() {
        getProducts().stream()
                .forEach(productModel ->
                        productService
                                .findProductAndDeleteFromCart(productModel.getCount(), productModel.getProductId()));
    }
}
