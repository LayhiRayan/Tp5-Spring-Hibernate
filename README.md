# TP5 — Intégration Spring et Hibernate

Projet Maven `microservice-product`, Java 11+, Spring 5.3.20 et Hibernate 5.6.9.Final, selon le TP. Entité Product, interface DAO générique, DAO Hibernate, service injecté par constructeur et transactions Spring.

## Préparer MySQL

Créer la base avec `CREATE DATABASE product_db;`. La configuration locale utilise l'utilisateur root avec un mot de passe vide (`jdbc.password=`).

```powershell
$env:JAVA_HOME="C:\Users\rayan\.jdks\ms-21.0.9"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
$env:JDBC_USERNAME="root"
Remove-Item Env:JDBC_PASSWORD -ErrorAction SilentlyContinue
mvn clean verify
mvn exec:java -Dexec.mainClass="com.microservice.product.TestHibernate"
mvn exec:java -Dexec.mainClass="com.microservice.product.MainApp"
```

MainApp ajoute un produit à chaque exécution. Les paramètres par défaut sont dans src/main/resources/application.properties ; les variables d'environnement ou propriétés système permettent de les remplacer. `hibernate.hbm2ddl.auto=update` sert au développement.

## Vérification

Les trois tests d'intégration utilisent une base H2 isolée : CRUD complet, prix négatif refusé et annulation d'une insertion après exception. Ils démarrent la véritable configuration Spring/Hibernate et utilisent le service transactionnel. Aucun serveur MySQL n'est nécessaire pour `mvn verify`. La connexion MySQL doit être vérifiée séparément avec TestHibernate après configuration locale.

Corrections du support : import org.hibernate.SessionFactory, ajout du pilote MySQL et de l'interface ProductService annoncée, fermeture automatique du contexte. Les transactions entourent les appels de service ; le DAO utilise getCurrentSession(). Les opérations update/delete gèrent les entités détachées avec merge.

Ce TP fournit les couches de persistance et de service. Une API REST et la découverte de services restent les prolongements proposés par le cours.
