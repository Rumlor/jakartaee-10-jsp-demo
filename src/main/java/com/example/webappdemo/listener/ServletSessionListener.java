package com.example.webappdemo.listener;

import com.example.webappdemo.model.CartModel;
import com.example.webappdemo.service.CartSessionService;
import com.example.webappdemo.service.ProductService;
import com.example.webappdemo.service.SessionAttributeWrapper;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.Optional;

@WebListener
public class ServletSessionListener implements HttpSessionListener {

    @EJB
    private ProductService productService;

    @EJB
    private CartSessionService cartSessionService;
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setMaxInactiveInterval(30);
        cartSessionService.setCartModelSessionAttributeWrapper(new SessionAttributeWrapper<>(session));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        CartModel cartModel = (CartModel) sessionEvent.getSession().getAttribute("cart");
        Optional.ofNullable(cartModel)
                .ifPresent(cart->cart.getProductModelList().stream()
                        .forEach(productModel -> productService.findProductAndDeleteFromCart(productModel.getCount(),productModel.getProductId())));
    }
}
