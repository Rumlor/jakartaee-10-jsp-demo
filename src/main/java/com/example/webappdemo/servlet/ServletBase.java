package com.example.webappdemo.servlet;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

public class ServletBase extends HttpServlet {

    protected String getEndpointFromRequest(HttpServletRequest req, String regex) {
        return req.getRequestURI().split(regex)[1];
    }
}
