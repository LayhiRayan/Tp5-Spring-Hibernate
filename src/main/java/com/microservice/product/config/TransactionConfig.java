package com.microservice.product.config;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@Configuration @EnableTransactionManagement
public class TransactionConfig {
 @Bean public HibernateTransactionManager transactionManager(SessionFactory factory){return new HibernateTransactionManager(factory);}
}
