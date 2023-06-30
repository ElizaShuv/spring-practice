package ru.sber.repository;

import org.springframework.stereotype.Repository;
import ru.sber.model.Basket;

import ru.sber.model.Product;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Класс, содержащий методы для работы с корзиной
 */

@Repository
public class BDBasketRepository implements BasketRepository{
    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?currentSchema=products_shuvalova_eu&user=postgres&password=12345";

    @Override
    public Optional<Basket> addProductToBasket(long basketId, long productId) {
        var insertSql = "INSERT INTO products_carts (id_product, id_cart, count) VALUES (?, ?, 1)";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(insertSql)) {
            prepareStatement.setLong(1, productId);
            prepareStatement.setLong(2, basketId);

            int rowsInserted = prepareStatement.executeUpdate();

            if (rowsInserted > 0) {
                Optional<Basket> basket = getBasketById(basketId);
                return basket;
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Optional<Basket> getBasketById(long basketId) {
        var selectBasketSql = "SELECT * FROM carts WHERE id = ?";
        var selectProductsSql = "SELECT p.id AS product_id, p.name AS product_name, p.price AS product_price, pc.count AS product_count " +
                "FROM products_carts pc " +
                "JOIN products p ON pc.id_product = p.id " +
                "WHERE pc.id_cart = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var basketStatement = connection.prepareStatement(selectBasketSql);
             var productsStatement = connection.prepareStatement(selectProductsSql)) {
            basketStatement.setLong(1, basketId);

            var basketResultSet = basketStatement.executeQuery();

            if (basketResultSet.next()) {
                int id = basketResultSet.getInt("id");
                String promoCode = basketResultSet.getString("promocode");

                List<Product> productList = new ArrayList<>();

                productsStatement.setLong(1, basketId);
                var productsResultSet = productsStatement.executeQuery();

                while (productsResultSet.next()) {
                    int productId = productsResultSet.getInt("product_id");
                    String productName = productsResultSet.getString("product_name");
                    BigDecimal productPrice = productsResultSet.getBigDecimal("product_price");
                    int productCount = productsResultSet.getInt("product_count");

                    Product product = new Product(productId, productName, productPrice, productCount);
                    productList.add(product);
                }

                Basket basket = new Basket(id, productList, promoCode);
                return Optional.of(basket);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Basket> changeProductCount(long basketId, long productId, int count) {
        var updateSql = "UPDATE products_carts SET count = ? WHERE id_cart = ? AND id_product = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(updateSql)) {
            prepareStatement.setInt(1, count);
            prepareStatement.setLong(2, basketId);
            prepareStatement.setLong(3, productId);

            int rowsUpdated = prepareStatement.executeUpdate();

            if (rowsUpdated > 0) {
                Optional<Basket> basket = getBasketById(basketId);
                return basket;
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<Basket> deleteProductToBasket(long basketId, long productId) {
        var deleteSql = "DELETE FROM products_carts WHERE id_cart = ? AND id_product = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(deleteSql)) {
            prepareStatement.setLong(1, basketId);
            prepareStatement.setLong(2, productId);

            int rowsDeleted = prepareStatement.executeUpdate();

            if (rowsDeleted > 0) {
                Optional<Basket> basket = getBasketById(basketId);
                return basket;
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


