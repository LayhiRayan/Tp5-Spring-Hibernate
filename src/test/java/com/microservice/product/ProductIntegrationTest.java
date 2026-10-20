package com.microservice.product;
import com.microservice.product.config.AppConfig;
import com.microservice.product.entity.Product;
import com.microservice.product.service.ProductService;
import org.junit.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.*;
import static org.junit.Assert.*;
public class ProductIntegrationTest {
 private AnnotationConfigApplicationContext ctx; private ProductService service;
 @Before public void start(){
  ctx=new AnnotationConfigApplicationContext();Map<String,Object> props=new HashMap<>();
  props.put("jdbc.driver","org.h2.Driver");props.put("jdbc.url","jdbc:h2:mem:"+UUID.randomUUID()+";DB_CLOSE_DELAY=-1");
  props.put("jdbc.username","sa");props.put("jdbc.password","");
  props.put("hibernate.dialect","org.hibernate.dialect.H2Dialect");props.put("hibernate.hbm2ddl.auto","create-drop");props.put("hibernate.show_sql","false");
  ctx.getEnvironment().getPropertySources().addFirst(new MapPropertySource("test",props));
  ctx.register(AppConfig.class);ctx.refresh();service=ctx.getBean(ProductService.class);
 }
 @After public void stop(){if(ctx!=null)ctx.close();}
 @Test public void crud(){
  Product p=new Product("Gateway",149.99);service.addProduct(p);assertNotNull(p.getId());
  assertEquals("Gateway",service.getProduct(p.getId()).getName());assertEquals(1,service.getAllProducts().size());
  p.setPrice(200);service.updateProduct(p);assertEquals(200,service.getProduct(p.getId()).getPrice(),0.001);
  service.deleteProduct(p.getId());assertNull(service.getProduct(p.getId()));assertTrue(service.getAllProducts().isEmpty());
 }
 @Test public void rejectsInvalidPrice(){
  try{service.addProduct(new Product("Invalid",-1));fail();}catch(IllegalArgumentException expected){}
  assertTrue(service.getAllProducts().isEmpty());
 }
 @Test public void rollback(){
  TransactionTemplate tx=new TransactionTemplate(ctx.getBean(PlatformTransactionManager.class));
  try{tx.execute(status->{service.addProduct(new Product("Rollback",20));throw new IllegalStateException("Simulated failure");});fail();}
  catch(IllegalStateException expected){}
  assertTrue(service.getAllProducts().isEmpty());
 }
}
