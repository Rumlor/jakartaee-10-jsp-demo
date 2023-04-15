package com.example.webappdemo.servlet;

import java.util.Objects;

public final class ServletPath {
    private ServletPath(){}

    public static final String ROOT = "/";
    public static  String INDEX = "index.jsp";
    public static  String LOGIN = "login.jsp";
    public static  String CART  = "cart.jsp";
    public static  String ORDERS = "orders.jsp";
    public static final String AUTH_LOGIN = "/auth/login";
    public static final String AUTH_LOGOUT = "/auth/logout";
    public static final String CART_ADD = "/cart/add";
}
