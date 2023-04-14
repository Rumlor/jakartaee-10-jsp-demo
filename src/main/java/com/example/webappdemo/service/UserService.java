package com.example.webappdemo.service;


import com.example.webappdemo.entity.User;

public interface UserService {
    User runQuery(String username, String pass);
}
