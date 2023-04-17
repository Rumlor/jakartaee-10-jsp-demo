package com.example.webappdemo.listener;

import com.example.webappdemo.beans.services.ProductService;
import com.example.webappdemo.beans.statefulbeans.CartOperation;
import com.example.webappdemo.model.CartModel;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.Optional;

@WebListener
public class ServletSessionListener implements HttpSessionListener {

    @EJB
    private ProductService productService;

    @EJB
    private CartOperation cartOperation;


    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        CartModel cartModel = cartOperation.getCart();
        Optional.ofNullable(cartModel)
                .ifPresent(cart->cart.getProductModelList().stream()
                        .forEach(productModel -> productService.findProductAndDeleteFromCart(productModel.getCount(),productModel.getProductId())));
    }
}
