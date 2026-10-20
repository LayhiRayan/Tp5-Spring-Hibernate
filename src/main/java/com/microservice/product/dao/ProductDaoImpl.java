package com.microservice.product.dao;
import com.microservice.product.entity.Product;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class ProductDaoImpl implements IDao<Product> {
 private final SessionFactory sessionFactory;
 public ProductDaoImpl(SessionFactory sessionFactory){this.sessionFactory=sessionFactory;}
 public void save(Product p){sessionFactory.getCurrentSession().save(p);}
 public void update(Product p){sessionFactory.getCurrentSession().merge(p);}
 public void delete(Product p){sessionFactory.getCurrentSession().delete(sessionFactory.getCurrentSession().merge(p));}
 public Product findById(Long id){return sessionFactory.getCurrentSession().get(Product.class,id);}
 public List<Product> findAll(){return sessionFactory.getCurrentSession().createQuery("FROM Product",Product.class).list();}
}
