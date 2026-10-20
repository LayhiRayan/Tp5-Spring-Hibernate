package com.microservice.product.service;
import com.microservice.product.dao.IDao;
import com.microservice.product.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @Transactional
public class ProductServiceImpl implements ProductService {
 private final IDao<Product> productDao;
 public ProductServiceImpl(IDao<Product> productDao){this.productDao=productDao;}
 private void validate(Product p){
  if(p==null || p.getName()==null || p.getName().trim().isEmpty()) throw new IllegalArgumentException("Nom du produit obligatoire");
  if(!Double.isFinite(p.getPrice()) || p.getPrice()<0) throw new IllegalArgumentException("Prix invalide : valeur positive ou nulle attendue");
 }
 public void addProduct(Product p){validate(p);productDao.save(p);}
 public void updateProduct(Product p){validate(p);if(p.getId()==null || productDao.findById(p.getId())==null) throw new IllegalArgumentException("Produit introuvable");productDao.update(p);}
 public void deleteProduct(Long id){Product p=productDao.findById(id);if(p==null) throw new IllegalArgumentException("Produit introuvable");productDao.delete(p);}
 @Transactional(readOnly=true) public Product getProduct(Long id){return productDao.findById(id);}
 @Transactional(readOnly=true) public List<Product> getAllProducts(){return productDao.findAll();}
}
