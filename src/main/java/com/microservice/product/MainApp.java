package com.microservice.product;
import com.microservice.product.config.AppConfig;
import com.microservice.product.entity.Product;
import com.microservice.product.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class MainApp {
 public static void main(String[] args){try(AnnotationConfigApplicationContext ctx=new AnnotationConfigApplicationContext(AppConfig.class)){
  ProductService service=ctx.getBean(ProductService.class);
  service.addProduct(new Product("Microservice Gateway",149.99));
  System.out.println("Produit ajoute avec succes ! Total en base : "+service.getAllProducts().size());
 }}
}
