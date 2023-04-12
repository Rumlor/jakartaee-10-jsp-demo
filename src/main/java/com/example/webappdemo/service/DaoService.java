package com.example.webappdemo.service;

import jakarta.ejb.Remote;


public interface DaoService<T> {
    T runQuery(String mail, String pass);
}
