package com.example.webappdemo.servlet;

import com.example.webappdemo.entity.Product;
import com.example.webappdemo.model.CartModel;
import com.example.webappdemo.model.ProductModel;
import com.example.webappdemo.service.ProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "cartServlet", urlPatterns =ServletPath.CART_ADD)
public class CartServlet extends ServletBase{

    @EJB
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        CartModel cartSessionObject = (CartModel) session.getAttribute("cart");
        var productID = req.getParameter("id");
        cartSessionObject = createNewSessionObjectIfNull(cartSessionObject);
        boolean successfullyAddedToCart = addToCart(cartSessionObject, productID);

        session.setAttribute("cart",cartSessionObject);
        if (successfullyAddedToCart)
            session.setAttribute("info","Item was successfully added to cart.");
        else
            session.setAttribute("error","Item could not be added to cart because insufficient stock.");
        try {
            resp.sendRedirect(ServletPath.ROOT + req.getContextPath().concat(ServletPath.INDEX));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean addToCart(CartModel cartSessionObject, String productID) {
        boolean successfullyAddedToCart  = true;
        try {
            addProductToCart(cartSessionObject, productID);
        }catch (RuntimeException e){
            successfullyAddedToCart = false;
        }
        return successfullyAddedToCart;
    }

    private  void addProductToCart(CartModel cartSessionObject, String productID) {
        Product product = productService.findProductAndAddToCart(Long.parseLong(productID));

        ProductModel productModel = ProductModel.builder()
                                .price(product.getPrice())
                                .category(product.getCategory())
                                .name(product.getName())
                                .productId(product.getId())
                                .build();
        cartSessionObject.addToCart(productModel);
    }

    private CartModel createNewSessionObjectIfNull(CartModel cartSessionObject) {
        return Optional.ofNullable(cartSessionObject).orElse(CartModel.builder().build());
    }
}
