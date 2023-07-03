package ru.sber.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sber.model.Product;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * Класс, содержащий методы для работы с продуктами
 */
@Repository
public class DBProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long save(Product product) {
        var insertSql = "INSERT INTO PRODUCTS (name, price, count) VALUES (?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setString(1, product.getProductName());
            prepareStatement.setDouble(2, product.getPrice().doubleValue());
            prepareStatement.setInt(3, product.getCount());
            return prepareStatement;
        };
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return (long) (int) keyHolder.getKeys().get("id");
    }
    @Override
    public Optional<Product> findById(long productId) {
        var selectSql = "SELECT * FROM PRODUCTS where id = ?";
        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(selectSql);
            prepareStatement.setLong(1, productId);

            return prepareStatement;
        };

        RowMapper<Product> productRowMapper = getProductRowMapper();

        List<Product> products = jdbcTemplate.query(preparedStatementCreator, productRowMapper);

        return products.stream().findFirst();
    }



    @Override
    public List<Product> findAll(String productName) {
        var selectSql = "SELECT * FROM PRODUCTS where name like ?";

            PreparedStatementCreator preparedStatementCreator = connection -> {
                var prepareStatement = connection.prepareStatement(selectSql);
                prepareStatement.setString(1, "%" + (productName == null ? "" : productName) + "%");
                return prepareStatement;
            };


            RowMapper<Product> productRowMapper = getProductRowMapper();

            return jdbcTemplate.query(preparedStatementCreator, productRowMapper);
        }

        public static RowMapper<Product> getProductRowMapper() {
            return (resultSet, rowNum) -> {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int count = resultSet.getInt("count");
                return new Product(id, name, BigDecimal.valueOf(price),count);
                };
        }
    @Override
    public boolean update(Product product) {

            var updateSql = """
                UPDATE PRODUCTS
                SET 
                name = ?,
                price = ?,
                count = ?
                where id = ?;
                """;

            PreparedStatementCreator preparedStatementCreator = connection -> {
                var prepareStatement = connection.prepareStatement(updateSql);
                prepareStatement.setString(1, product.getProductName());
                prepareStatement.setDouble(2, product.getPrice().doubleValue());
                prepareStatement.setInt(3, product.getCount());
                prepareStatement.setLong(4, product.getProductId());

                return prepareStatement;
            };
            int rows = jdbcTemplate.update(preparedStatementCreator);

            return rows > 0;
        }
        @Override
        public boolean deleteById(long id) {
        var deleteSql = "DELETE FROM PRODUCTS where id = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(deleteSql);
            prepareStatement.setLong(1, id);
            return prepareStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);

        return rows > 0;
    }

}


