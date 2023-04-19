package com.example.webappdemo.beans.services;


import com.example.webappdemo.entity.User;

public interface UserService {
    User runQuery(String username, String pass);
}
