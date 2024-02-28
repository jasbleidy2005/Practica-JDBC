package org.example.repository.impl;

import org.example.modelo.Product;
import org.example.repository.Repository;
import org.example.singleton.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryImpl implements Repository<Product> {

    private Connection getConnection() throws SQLException {
        return DataBaseConnection.getInstance();
    }

    private Product createProduct(ResultSet resultSet) throws SQLException {
        Product producto = new Product();
        producto.setId(String.valueOf(resultSet.getInt("id")));
        producto.setName(resultSet.getString("nombre"));
        producto.setAuthor(resultSet.getString("autora"));
        producto.setEditorial(resultSet.getString("editorial"));
        producto.setPrice(resultSet.getDouble("precio"));
        return producto;
    }

    @Override
    public List<Product> list() {
        List<Product> productoList = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT *from libros")) {
            while (resultSet.next()) {
                Product product = createProduct(resultSet);
                productoList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productoList;
    }

    @Override
    public Product byId(long id) {
        Product product = null;
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement("SELECT  * FROM  libros WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = createProduct(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public void save(Product product) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO libros(nombre,autora,editorial,precio) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2,product.getAuthor());
            preparedStatement.setString(3,product.getEditorial());
            preparedStatement.setDouble(4,product.getPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(long id) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                "DELETE FROM libros WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Product product) {
                try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                        "UPDATE libros SET nombre = ?, autora = ?, editorial = ?, precio = ? WHERE id = ?")) {
                    preparedStatement.setString(1, product.getName());
                    preparedStatement.setString(2, product.getAuthor());
                    preparedStatement.setString(3, product.getEditorial());
                    preparedStatement.setDouble(4, product.getPrice());
                    preparedStatement.setLong(5, Long.parseLong(product.getId()));

                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
    }
