package com.example.webappdemo;

import com.example.webappdemo.entity.User;
import com.example.webappdemo.service.UserDaoService;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {

    @EJB
    private UserDaoService userDaoService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        var username = req.getParameter("username");
        var pass = req.getParameter("password");
        User user = userDaoService.runQuery(username,pass);
        try {
            if (user != null){
                req.getSession().setAttribute("auth",user);
                req.getSession().removeAttribute("error");
                resp.sendRedirect("index.jsp");
            }
            else {
                req.getSession().setAttribute("error",true);
                resp.sendRedirect("login.jsp");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}