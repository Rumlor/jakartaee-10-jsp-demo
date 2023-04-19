package com.example.webappdemo.servlet;

import com.example.webappdemo.beans.services.ProductService;
import com.example.webappdemo.beans.statefulbeans.CartOperation;
import com.example.webappdemo.entity.Product;
import com.example.webappdemo.model.ProductModel;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "cartServlet", urlPatterns = {ServletPath.CART_ADD,ServletPath.CART_DELETE,ServletPath.CART_REMOVE,ServletPath.CART_SET})
public class CartServlet extends ServletBase{
    private final ProductService productServiceBean;

    private final CartOperation cartOperationBean;

    @Inject
    public CartServlet(ProductService productServiceBean, CartOperation cartOperationBean) {
        this.productServiceBean = productServiceBean;
        this.cartOperationBean = cartOperationBean;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
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
            case "set":
                cartSet(req,resp);
                break;
        }

    }

    private void cartSet(HttpServletRequest req, HttpServletResponse resp) {
        String productID = req.getParameter("id");
        String count = req.getParameter("count");
        boolean successful = true;
        Integer setCount = 0;
        try {
            setCount =  setToCart(productID,count);
        }catch (Exception e){
            successful = false;
        }
        if (successful) {
            if (setCount > 0)
                cartOperationBean.setInfo(setCount + " item was successfully added to the cart.");
            else
                cartOperationBean.setInfo((-setCount) + " item was successfully removed from the cart.");

        }
        else
            cartOperationBean.setError("Item could not be added to cart because insufficient stock.");

        finishRequestWithRedirect(resp, req, ServletPath.CART);
    }

    private Integer setToCart(String productID, String count) {
        Long productId = Long.parseLong(productID);
        Integer productCount = Integer.parseInt(count);
        ProductModel productInCart =  cartOperationBean.getProducts().stream().filter(p->p.getProductId().equals(productId)).findFirst().orElseThrow(()-> new RuntimeException("No product with id "+productID+" was found in cart"));
        Integer changeInCount = productCount - productInCart.getCount();
        Product product = productServiceBean.findProductAndChangeFromCart(changeInCount,productId);
        ProductModel newProductModel =  ProductModel.builder().productId(productId).count(productCount).price(product.getPrice()).name(product.getName()).category(product.getCategory()).build();
        cartOperationBean.setProductToCart(newProductModel);
        return changeInCount;

    }

    private void cartRemove(HttpServletRequest req, HttpServletResponse resp) {
        String productID = req.getParameter("id");
        removeFromCart(productID);
        cartOperationBean.setInfo("1 item was successfully removed from the cart");
        finishRequestWithRedirect(resp, req, ServletPath.CART);
    }

    private void removeFromCart( String productID) {
        ProductModel productModelInCart =  cartOperationBean.getProducts().stream()
                .filter(productModel -> productModel.getProductId().equals(Long.parseLong(productID)))
                .findFirst().orElseThrow(()->new RuntimeException("Product with id " + productID +" could not be found"));

        productModelInCart.setCount(productModelInCart.getCount()-1);
        productServiceBean.findProductAndDeleteFromCart(1,Long.parseLong(productID));
        if (productModelInCart.getCount() == 0)
            cartOperationBean.removeProductFromCart(productModelInCart);
    }

    private void cartDelete(HttpServletRequest req, HttpServletResponse resp) {
        String productID = req.getParameter("id");
        deleteFromCart(productID);
        cartOperationBean.setInfo("Item was successfully removed from the cart.");
        cartOperationBean.setError(null);
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
        productServiceBean.findProductAndDeleteFromCart(productModelInCart.getCount(),Long.parseLong(productID));
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
        Product product = productServiceBean.findProductAndAddToCart(Long.parseLong(productID));

        ProductModel productModel = ProductModel.builder()
                                .price(product.getPrice())
                                .category(product.getCategory())
                                .name(product.getName())
                                .productId(product.getId())
                                .build();

        cartOperationBean.addProductToCart(productModel);
    }

}
