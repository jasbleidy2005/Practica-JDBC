package org.example.application;

import org.example.modelo.Product;
import org.example.repository.Repository;
import org.example.repository.impl.ProductRepositoryImpl;
import org.example.singleton.DataBaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main2 {
    public static void main(String[] args){
        try (Connection conn = DataBaseConnection.getInstance()){
            Repository<Product> repository = new ProductRepositoryImpl();
            System.out.println("****** List products from database");
            repository.list().stream().forEach(System.out::println);

            System.out.println("***** Get by id : 1");
            System.out.println(repository.byId(1).toString());

            System.out.println("***** save");
            Product product = new Product();
            product.setName("Fingiendo amor");
            product.setAuthor("Karen mata");
            product.setEditorial("Naranja");
            product.setPrice(55000);
            repository.save(product);
            repository.list().forEach(System.out::println);

            System.out.println("*****Delete");
            //repository.delete(1);
            //repository.list().forEach(System.out::println);

            System.out.println("*****Update");
            Product updatedProduct = new Product();
            updatedProduct.setId("2");
            updatedProduct.setName("A través de mi ventana");
            updatedProduct.setAuthor("Ariana Godoy");
            updatedProduct.setEditorial("AlfaGuara");
            updatedProduct.setPrice(46500.0);

            repository.update(updatedProduct);
            repository.list().forEach(System.out::println);

        }catch (SQLException e){
            e.printStackTrace();



        }
    }
}
