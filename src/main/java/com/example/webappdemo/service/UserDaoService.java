package com.example.webappdemo.service;


import com.example.webappdemo.entity.User;

public interface UserDaoService {
    User runQuery(String username, String pass);
}
