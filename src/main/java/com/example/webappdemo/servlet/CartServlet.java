package com.example.webappdemo.servlet;

import com.example.webappdemo.entity.Product;
import com.example.webappdemo.model.CartModel;
import com.example.webappdemo.model.ProductModel;
import com.example.webappdemo.service.CartSessionService;
import com.example.webappdemo.service.ProductService;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "cartServlet", urlPatterns = {ServletPath.CART_ADD,ServletPath.CART_DELETE,ServletPath.CART_REMOVE})
public class CartServlet extends ServletBase{

    @EJB
    private ProductService productService;

    @EJB
    private CartSessionService cartSessionService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String endpointFromRequest = getEndpointFromRequest(req,"/cart/");
        switch (endpointFromRequest) {
            case "add":
                cartAdd(req, resp);
                break;
            case "delete":
                cartDelete(req, resp);
                break;
            case "remove":
                cartRemove(req, resp);
                break;
        }

    }

    private void cartRemove(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        CartModel cartSessionObject = cartSessionService.getCartModelFromSessionOrCreateNew();
        String productID = req.getParameter("id");
        removeFromCart(cartSessionObject,productID);
        cartSessionService.setCartSessionAttribute(cartSessionObject);
        session.setAttribute("info","1 Item was successfully removed from the cart.");
        session.removeAttribute("error");
        finishRequestWithRedirect(resp, req, ServletPath.CART);
    }

    private void removeFromCart(CartModel cartSessionObject, String productID) {
        ProductModel productModelInCart =  cartSessionObject
                .getProductModelList().stream()
                .filter(productModel -> productModel.getProductId().equals(Long.parseLong(productID)))
                .findFirst().get();
        productModelInCart.setCount(productModelInCart.getCount()-1);
        productService.findProductAndDeleteFromCart(1,Long.parseLong(productID));
        if (productModelInCart.getCount() == 0)
            cartSessionObject.getProductModelList().remove(productModelInCart);
    }

    private void cartDelete(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        CartModel cartSessionObject = cartSessionService.getCartModelFromSessionOrCreateNew();
        String productID = req.getParameter("id");
        deleteFromCart(cartSessionObject,productID);
        cartSessionService.setCartSessionAttribute(cartSessionObject);
        session.setAttribute("info","Item was successfully removed from the cart.");
        session.removeAttribute("error");
        finishRequestWithRedirect(resp, req, ServletPath.CART);
    }

    private void finishRequestWithRedirect(HttpServletResponse resp, HttpServletRequest req, String CART) {
        try {
            resp.sendRedirect(ServletPath.ROOT + req.getContextPath().concat(CART));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        CartModel cartSessionObject = cartSessionService.getCartModelFromSessionOrCreateNew();
        String productID = req.getParameter("id");
        boolean successfullyAddedToCart = addToCart(productID);
        cartSessionService.setCartSessionAttribute(cartSessionObject);
        if (successfullyAddedToCart)
            session.setAttribute("info","Item was successfully added to cart.");
        else
            session.setAttribute("error","Item could not be added to cart because insufficient stock.");
        finishRequestWithRedirect(resp, req, ServletPath.INDEX);
    }

    private boolean addToCart( String productID) {
        boolean successfullyAddedToCart  = true;
        try {
            addProductToCart(productID);
        }catch (RuntimeException e){
            successfullyAddedToCart = false;
        }
        return successfullyAddedToCart;
    }

    private  void addProductToCart(String productID) {
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
