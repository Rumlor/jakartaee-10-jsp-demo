package com.example.webappdemo.servlet;

import com.example.webappdemo.entity.Product;
import com.example.webappdemo.model.CartModel;
import com.example.webappdemo.model.ProductModel;
import com.example.webappdemo.service.CartSessionService;
import com.example.webappdemo.service.ProductService;
import com.example.webappdemo.service.SessionAttributeWrapper;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBs;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@WebServlet(name = "cartServlet", urlPatterns = {ServletPath.CART_ADD,ServletPath.CART_DELETE})
public class CartServlet extends ServletBase{

    @EJB
    private ProductService productService;

    private CartSessionService cartSessionService ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String endpointFromRequest = getEndpointFromRequest(req,"/cart/");
        if (endpointFromRequest.equals("add"))
            cartAdd(req, resp);
        else if (endpointFromRequest.equals("delete"))
            cartDelete(req,resp);
    }

    private void cartDelete(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        cartSessionService = createSessionServiceOrGetNew(session);
        CartModel cartSessionObject = cartSessionService.getCartModelFromSessionOrCreateNew();
        String productID = req.getParameter("id");
        deleteFromCart(cartSessionObject,productID);
        cartSessionService.setCartSession(cartSessionObject);
        session.setAttribute("info","Item was successfully removed from the cart.");
        session.removeAttribute("error");
        try {
            resp.sendRedirect(ServletPath.ROOT + req.getContextPath().concat(ServletPath.CART));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private  CartSessionService createSessionServiceOrGetNew(HttpSession session) {
        return Objects.isNull(cartSessionService) ? new CartSessionService("cart", new SessionAttributeWrapper<>(session)) : cartSessionService;
    }

    private void deleteFromCart(CartModel cartSessionObject, String productID) {
        ProductModel productModelInCart =  cartSessionObject
                                        .getProductModelList().stream()
                                        .filter(productModel -> productModel.getProductId().equals(Long.parseLong(productID)))
                                        .findFirst().get();
        productService.findProductAndDeleteFromCart(productModelInCart.getCount(),Long.parseLong(productID));
        cartSessionService.removeProductFromSession(productModelInCart);
    }

    private void cartAdd(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        cartSessionService = createSessionServiceOrGetNew(session);
        CartModel cartSessionObject = cartSessionService.getCartModelFromSessionOrCreateNew();
        String productID = req.getParameter("id");
        boolean successfullyAddedToCart = addToCart(cartSessionObject, productID);
        cartSessionService.setCartSession(cartSessionObject);
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
        cartSessionService.addProductToCartSession(productModel);
    }

}
