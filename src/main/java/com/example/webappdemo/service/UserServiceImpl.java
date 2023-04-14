package com.example.webappdemo.service;

import com.example.webappdemo.entity.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Stateless
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User runQuery(String username, String pass) {
        User user = null;

        try {
        user =  entityManager.createQuery("select u from User u where u.userName= :username and u.password = :pass", User.class)
                    .setParameter("username", username)
                    .setParameter("pass", pass)
                    .getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

}
