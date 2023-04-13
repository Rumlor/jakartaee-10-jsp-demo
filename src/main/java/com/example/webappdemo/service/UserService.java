package com.example.webappdemo.service;

import com.example.webappdemo.entity.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@Stateless
public class UserService implements UserDaoService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public User runQuery(String mail, String pass) {
        User user = null;

        try {
        user =  entityManager.createQuery("select u from User u where u.email= :mail and u.password = :pass", User.class)
                    .setParameter("mail", mail)
                    .setParameter("pass", pass)
                    .getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

}
