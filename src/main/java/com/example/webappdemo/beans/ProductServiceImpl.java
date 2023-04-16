package com.example.webappdemo.beans;

import com.example.webappdemo.entity.Product;
import jakarta.ejb.Stateless;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
@Named("productBean")
public class ProductServiceImpl implements ProductService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> getAllProducts() {
        return entityManager.createQuery("select p from Product p", Product.class).getResultList();
    }

    @Override
    public Product findProductAndAddToCart(Long productId) {
        Product product =  entityManager.find(Product.class, productId);
        if (product.getCount() > 0)
            product.decrementStock();
        else
            throw new RuntimeException("Stock is not sufficient for "+product.getName());
        return product;
    }

    @Override
    public void findProductAndDeleteFromCart(Integer count, Long productId) {
        entityManager
                .createQuery("update Product  p set p.count = p.count+ :count where p.id =:productId", Product.class)
                .setParameter("productId",productId)
                .setParameter("count",count)
                .executeUpdate();
    }

}
