package com.example.webappdemo.beans.services;

import com.example.webappdemo.entity.Product;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Named("productBean")
public class ProductServiceImpl implements ProductService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> getAllProducts() {
        return entityManager.createQuery("select p from Product p", Product.class).getResultList();
    }

    @Override
    @Transactional
    public Product findProductAndAddToCart(Long productId) {
        Product product =  entityManager.find(Product.class, productId);
        if (product.getCount() > 0)
            product.decrementStock(1);
        else
            throw new RuntimeException("Stock is not sufficient for "+product.getName());
        return product;
    }

    @Override
    @Transactional
    public void findProductAndDeleteFromCart(Integer count, Long productId) {
        entityManager
                .createQuery("update Product  p set p.count = p.count+ :count where p.id =:productId", Product.class)
                .setParameter("productId",productId)
                .setParameter("count",count)
                .executeUpdate();
    }

    @Override
    @Transactional
    public Product findProductAndChangeFromCart(Integer count, Long productId) {
        Product product =  entityManager.find(Product.class, productId);
        if ((product.getCount()-count) > 0 ){
            product.decrementStock(count);
        }
        return product;
    }

}
