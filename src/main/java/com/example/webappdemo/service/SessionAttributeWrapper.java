package com.example.webappdemo.service;

import jakarta.servlet.http.HttpSession;

public class SessionAttributeWrapper <T>{
    private HttpSession session;

    public SessionAttributeWrapper(HttpSession session){
        this.session = session;
    }
    public T getSessionAttribute(String attr){
        return (T)session.getAttribute(attr);
    }
    public void setSessionAttribute(String attr,T model){
        session.setAttribute(attr,model);
    }
}
