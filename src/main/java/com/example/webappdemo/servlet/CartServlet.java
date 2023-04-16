package com.example.webappdemo.servlet;

import com.example.webappdemo.beans.services.ProductService;
import com.example.webappdemo.beans.statefulbeans.CartOperation;
import com.example.webappdemo.entity.Product;
import com.example.webappdemo.model.ProductModel;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "cartServlet", urlPatterns = {ServletPath.CART_ADD,ServletPath.CART_DELETE,ServletPath.CART_REMOVE})
public class CartServlet extends ServletBase{

    @EJB
    private ProductService productService;

    @EJB
    private CartOperation cartOperationBean;


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
        String productID = req.getParameter("id");
        removeFromCart(productID);
        cartOperationBean.setInfo("1 item was successfully removed from the cart");
        req.getSession().setAttribute("cartBean",cartOperationBean);
        finishRequestWithRedirect(resp, req, ServletPath.CART);
    }

    private void removeFromCart( String productID) {
        ProductModel productModelInCart =  cartOperationBean.getProducts().stream()
                .filter(productModel -> productModel.getProductId().equals(Long.parseLong(productID)))
                .findFirst().orElseThrow(()->new RuntimeException("Product with id " + productID +" could not be found"));

        productModelInCart.setCount(productModelInCart.getCount()-1);
        productService.findProductAndDeleteFromCart(1,Long.parseLong(productID));
        if (productModelInCart.getCount() == 0)
            cartOperationBean.removeProductFromCart(productModelInCart);
    }

    private void cartDelete(HttpServletRequest req, HttpServletResponse resp) {
        String productID = req.getParameter("id");
        deleteFromCart(productID);
        cartOperationBean.setInfo("Item was successfully removed from the cart.");
        cartOperationBean.setError(null);
        req.getSession().setAttribute("cartBean",cartOperationBean);
        finishRequestWithRedirect(resp, req, ServletPath.CART);
    }

    private void finishRequestWithRedirect(HttpServletResponse resp, HttpServletRequest req, String CART) {
        try {
            resp.sendRedirect(ServletPath.ROOT + req.getContextPath().concat(CART));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private void deleteFromCart(String productID) {
        ProductModel productModelInCart =  cartOperationBean.getProducts().stream()
                                        .filter(productModel -> productModel.getProductId().equals(Long.parseLong(productID)))
                                        .findFirst().get();
        productService.findProductAndDeleteFromCart(productModelInCart.getCount(),Long.parseLong(productID));
        cartOperationBean.removeProductFromCart(productModelInCart);
    }

    private void cartAdd(HttpServletRequest req, HttpServletResponse resp) {
        String productID = req.getParameter("id");
        boolean successfullyAddedToCart = addToCart(productID);

        if (successfullyAddedToCart)
            cartOperationBean.setInfo("Item was successfully added to cart");
        else
            cartOperationBean.setError("Item could not be added to cart because insufficient stock.");

         boolean inCart = Boolean.parseBoolean(req.getParameter("inCart"));
         req.getSession().setAttribute("cartBean",cartOperationBean);
         if (!inCart)
            finishRequestWithRedirect(resp, req, ServletPath.INDEX);
         else
             finishRequestWithRedirect(resp,req,ServletPath.CART);
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

        cartOperationBean.addProductToCart(productModel);
    }

}
