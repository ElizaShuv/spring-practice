package ru.sber.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.sber.model.Cart;

import ru.sber.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Класс, содержащий методы для работы с корзиной
 */

@Repository
public class DBCartRepository implements CartRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBCartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean addProductToBasket(long basketId, long productId, int count) {
        boolean productExists = checkIfProductExistsInBasket(basketId, productId);

        if (productExists) {
            updateProductCountInBasket(basketId, productId, count);
        } else {
            insertProductIntoBasket(basketId, productId, count);
        }

        return true;
    }

    private boolean checkIfProductExistsInBasket(long basketId, long productId) {
        var selectSql = "SELECT COUNT(*) FROM products_carts WHERE id_product = ? AND id_cart = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setLong(1, productId);
            preparedStatement.setLong(2, basketId);
            return preparedStatement;
        };

        RowMapper<Integer> countRowMapper = (resultSet, rowNum) -> resultSet.getInt(1);

        List<Integer> countList = jdbcTemplate.query(preparedStatementCreator, countRowMapper);

        int count = countList.get(0);

        return count > 0;
    }

    private void updateProductCountInBasket(long basketId, long productId, int count) {
        var updateSql = "UPDATE products_carts SET count = count + ? WHERE id_product = ? AND id_cart = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setInt(1, count);
            preparedStatement.setLong(2, productId);
            preparedStatement.setLong(3, basketId);
            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator);
    }

    private void insertProductIntoBasket(long basketId, long productId, int count) {
        var insertSql = "INSERT INTO products_carts (id_product, id_cart, count) VALUES (?, ?, ?)";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setLong(1, productId);
            preparedStatement.setLong(2, basketId);
            preparedStatement.setInt(3, count);
            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator);
    }


    @Override
    public Optional<Cart> getBasketById(long basketId) {
        var selectBasketSql = "SELECT * FROM carts WHERE id = ?";
        var selectProductsSql = "SELECT p.id AS product_id, p.name AS product_name, p.price AS product_price, pc.count AS product_count " +
                "FROM products_carts pc " +
                "JOIN products p ON pc.id_product = p.id " +
                "WHERE pc.id_cart = ?";

        PreparedStatementCreator basketPreparedStatementCreator = connection -> {
            var basketStatement = connection.prepareStatement(selectBasketSql);
            basketStatement.setLong(1, basketId);
            return basketStatement;
        };

        PreparedStatementCreator productsPreparedStatementCreator = connection -> {
            var productsStatement = connection.prepareStatement(selectProductsSql);
            productsStatement.setLong(1, basketId);
            return productsStatement;
        };

        RowMapper<Cart> basketRowMapper = getBasketRowMapper();
        RowMapper<Product> productRowMapper = getProductRowMapper();

        List<Cart> baskets = jdbcTemplate.query(basketPreparedStatementCreator, basketRowMapper);

        if (!baskets.isEmpty()) {
            Cart basket = baskets.get(0);
            basket.setProductList(jdbcTemplate.query(productsPreparedStatementCreator, productRowMapper));
            return Optional.of(basket);
        }

        return Optional.empty();
    }

    public RowMapper<Cart> getBasketRowMapper() {
        return (resultSet, rowNum) -> {
            int id = resultSet.getInt("id");
            String promoCode = resultSet.getString("promocode");
            return new Cart(id, new ArrayList<>(), promoCode);
        };
    }

    public RowMapper<Product> getProductRowMapper() {
        return (resultSet, rowNum) -> {
            int productId = resultSet.getInt("product_id");
            String productName = resultSet.getString("product_name");
            BigDecimal productPrice = resultSet.getBigDecimal("product_price");
            int productCount = resultSet.getInt("product_count");
            return new Product(productId, productName, productPrice, productCount);
        };
    }


    @Override
    public Optional<Cart> changeProductCount(long basketId, long productId, int count) {
        var updateSql = "UPDATE products_carts SET count = ? WHERE id_cart = ? AND id_product = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(updateSql);
            prepareStatement.setInt(1, count);
            prepareStatement.setLong(2, basketId);
            prepareStatement.setLong(3, productId);

            return prepareStatement;
        };

        int rowsUpdated = jdbcTemplate.update(preparedStatementCreator);

        if (rowsUpdated > 0) {
            return getBasketById(basketId);
        }

        return Optional.empty();
    }



    @Override
    public Optional<Cart> deleteProductFromBasket(long basketId, long productId) {
        var deleteSql = "DELETE FROM products_carts WHERE id_cart = ? AND id_product = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(deleteSql);
            prepareStatement.setLong(1, basketId);
            prepareStatement.setLong(2, productId);

            return prepareStatement;
        };

        int rowsDeleted = jdbcTemplate.update(preparedStatementCreator);

        if (rowsDeleted > 0) {
            return getBasketById(basketId);
        }

        return Optional.empty();
    }


}


