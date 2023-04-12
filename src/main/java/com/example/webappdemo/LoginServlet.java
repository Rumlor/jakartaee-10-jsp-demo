package com.example.webappdemo;

import com.example.webappdemo.entity.User;
import com.example.webappdemo.service.DaoService;
import jakarta.ejb.EJB;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "loginServlet", value = "/login-servlet")
public class LoginServlet extends HttpServlet {

    @EJB
    private DaoService<User> userDaoService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        var mail = req.getParameter("email");
        var pass = req.getParameter("password");
        User user = userDaoService.runQuery(mail,pass);
        try {
            if (user != null){
                req.getSession().setAttribute("auth",user);
                resp.sendRedirect("index.jsp");
            }
            else {
                req.getSession().setAttribute("error",true);
                resp.setStatus(401);
                resp.sendRedirect("login.jsp");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}