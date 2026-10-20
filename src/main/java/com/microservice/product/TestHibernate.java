package com.microservice.product;
import com.microservice.product.config.AppConfig;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class TestHibernate {
 public static void main(String[] args){try(AnnotationConfigApplicationContext ctx=new AnnotationConfigApplicationContext(AppConfig.class)){
  try(org.hibernate.Session session=ctx.getBean(SessionFactory.class).openSession()){
   session.doWork(connection->{if(!connection.isValid(5)) throw new java.sql.SQLException("Connexion invalide");});
  }
  System.out.println("Contexte Spring et connexion Hibernate initialises avec succes !");
 }}
}
