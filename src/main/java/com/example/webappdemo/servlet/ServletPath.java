package com.example.webappdemo.servlet;

import java.util.Objects;

public final class ServletPath {
    private ServletPath(){}
    private static String CONTEXT_PATH;
    public static  String INDEX = "index.jsp";
    public static  String LOGIN = "login.jsp";
    public static  String CART  = "cart.jsp";
    public static  String ORDERS = "orders.jsp";

    public static void setContextPath(String contextPath){
        if (Objects.isNull(CONTEXT_PATH) || Objects.equals(contextPath, "")) {
            CONTEXT_PATH = contextPath;
            INDEX = CONTEXT_PATH + INDEX;
            LOGIN = CONTEXT_PATH + LOGIN;
            CART = CONTEXT_PATH + CART;
            ORDERS = CONTEXT_PATH + ORDERS;
        }
    }
}
