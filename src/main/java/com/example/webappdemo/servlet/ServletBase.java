package com.example.webappdemo.servlet;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

public class ServletBase extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletPath.setContextPath(config.getServletContext().getContextPath());
    }
}
