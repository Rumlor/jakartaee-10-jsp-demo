package com.example.webappdemo.servlet;

import com.example.webappdemo.entity.User;
import com.example.webappdemo.service.UserService;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "loginServlet", urlPatterns = {ServletPath.AUTH_LOGIN,ServletPath.AUTH_LOGOUT})
public class LoginServlet extends ServletBase {

    @EJB
    private UserService userDaoService;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        routeToSpecificService(req,resp);
    }

    private void routeToSpecificService(HttpServletRequest req,HttpServletResponse resp) {
        String splitPath = req.getRequestURI().split("/auth/")[1];
        if (splitPath.equals("login"))
            loginService(req,resp);
        else
            logoutService(req,resp);
    }

    private void loginService(HttpServletRequest req, HttpServletResponse resp) {
        var username = req.getParameter("username");
        var pass = req.getParameter("password");
        User user = userDaoService.runQuery(username,pass);
        try {
            if (user != null){
                req.getSession().setAttribute("auth",user);
                req.getSession().removeAttribute("error");
                resp.sendRedirect(ServletPath.ROOT + req.getContextPath().concat(ServletPath.INDEX));
            }
            else {
                req.getSession().setAttribute("error",true);
                resp.sendRedirect(ServletPath.ROOT + req.getContextPath().concat(ServletPath.LOGIN));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void logoutService(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getSession().getAttribute("auth") != null){
            req.getSession().removeAttribute("auth");
        }
        try {
            resp.sendRedirect(ServletPath.ROOT + req.getContextPath().concat(ServletPath.LOGIN));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}