package com.example.webappdemo.beans;

import jakarta.servlet.http.HttpSession;

public class SessionAttributeWrapper <T>{
    private final HttpSession session;

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
