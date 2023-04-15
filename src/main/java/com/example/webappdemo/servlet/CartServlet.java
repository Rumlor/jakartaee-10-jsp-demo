package com.example.webappdemo.servlet;

import com.example.webappdemo.model.CartModel;
import com.example.webappdemo.model.ProductModel;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "cartServlet", urlPatterns =ServletPath.CART_ADD)
public class CartServlet extends ServletBase{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        var cartSessionObject = session.getAttribute("cart");
        var productID = req.getParameter("id");
        if (cartSessionObject == null){
            CartModel cart = new CartModel();
            ProductModel productModel = new ProductModel();
            productModel.setProductId(Long.parseLong(productID));
            productModel.setProductCount(1);
            cart.addToCart(productModel);
            session.setAttribute("cart",cart);
        }
    }
}
