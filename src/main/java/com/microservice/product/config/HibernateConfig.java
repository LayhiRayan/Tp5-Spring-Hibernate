package com.microservice.product.config;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import javax.sql.DataSource;
import java.util.Properties;
@Configuration @PropertySource("classpath:application.properties")
public class HibernateConfig {
 @Bean public DataSource dataSource(Environment env){
  DriverManagerDataSource ds=new DriverManagerDataSource();
  ds.setDriverClassName(env.getRequiredProperty("jdbc.driver"));
  ds.setUrl(env.getRequiredProperty("jdbc.url"));
  ds.setUsername(env.getRequiredProperty("jdbc.username"));
  ds.setPassword(env.getProperty("jdbc.password",""));return ds;
 }
 @Bean public LocalSessionFactoryBean sessionFactory(DataSource ds,Environment env){
  LocalSessionFactoryBean factory=new LocalSessionFactoryBean();factory.setDataSource(ds);
  factory.setPackagesToScan("com.microservice.product.entity");
  Properties props=new Properties();
  for(String key:new String[]{"hibernate.dialect","hibernate.show_sql","hibernate.hbm2ddl.auto"}) props.setProperty(key,env.getRequiredProperty(key));
  factory.setHibernateProperties(props);return factory;
 }
}
