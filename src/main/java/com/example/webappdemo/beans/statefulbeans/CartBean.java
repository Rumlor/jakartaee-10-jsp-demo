package com.example.webappdemo.beans.statefulbeans;

import com.example.webappdemo.beans.services.ProductService;
import com.example.webappdemo.model.CartModel;
import com.example.webappdemo.model.ProductModel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@SessionScoped
@Named("cartBean")
public class CartBean implements  CartOperation, Serializable,BeanLifeCycle {

    private final CartModel cart;
    private String cartInfoMessage;
    private String cartErrorMessage;

    private final ProductService productService;

    @Inject
    public CartBean(ProductService productService){
        this.productService = productService;
        cart = CartModel.builder().build();
    }


    @Override
    public void addProductToCart(ProductModel productModel) {
        cart.addToCart(productModel);
    }

    @Override
    public void setProductToCart(ProductModel product) {
        cart.setProductModelList(cart.getProductModelList().stream().filter(p->!p.getProductId().equals(product.getProductId())).collect(Collectors.toList()));
        if(product.getCount() > 0)
            cart.getProductModelList().add(product);
        cart.getProductModelList().sort(Comparator.comparing(ProductModel::getName));
    }

    @Override
    public void removeProductFromCart(ProductModel productModel) {
        cart.getProductModelList().remove(productModel);
    }

    @Override
    public BigDecimal getCartTotalPrice() {
        return cart.getProductModelList().stream()
                .map(p->p.getPrice().multiply(BigDecimal.valueOf(p.getCount())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public void emptyCart() {
        getProducts()
                .forEach(productModel ->
                        productService
                                .findProductAndDeleteFromCart(productModel.getCount(), productModel.getProductId()));
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
    public void resetInfoAndError() {
        this.setError(null);
        this.setInfo(null);
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

    }

    @PreDestroy
    public void destroyed() {
        this.emptyCart();
    }
}
