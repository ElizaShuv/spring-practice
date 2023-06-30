package ru.sber.repository;

import org.springframework.stereotype.Repository;
import ru.sber.model.Product;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс, содержащий методы для работы с продуктами
 */
@Repository
public class BDProductRepository implements ProductRepository {

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?currentSchema=products_shuvalova_eu&user=postgres&password=12345";

    @Override
    public long save(Product product) {
        var insertSql = "INSERT INTO PRODUCTS (name, price, count) VALUES (?,?,?);";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, product.getProductName());
            prepareStatement.setDouble(2, product.getPrice().doubleValue());
            prepareStatement.setInt(3, product.getCount());
            prepareStatement.executeUpdate();

            ResultSet rs = prepareStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Optional<Product> findById(long productId) {
        var selectSql = "SELECT * FROM PRODUCTS where id = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setLong(1, productId);

            var resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                int count = resultSet.getInt("count");
                Product product = new Product(id, name, price, count);

                return Optional.of(product);
            }

            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findAll(String productName) {
        var selectSql = "SELECT * FROM PRODUCTS where name like ?";
        List<Product> products = new ArrayList<>();

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setString(1, "%" + (productName == null ? "" : productName) + "%");

            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int count = resultSet.getInt("count");
                Product product = new Product(id, name, BigDecimal.valueOf(price),count);

                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean update(Product product) {

            var selectSql = """
                UPDATE PRODUCTS
                SET 
                name = ?,
                price = ?,
                count = ?
                where id = ?;
                """;

            try (var connection = DriverManager.getConnection(JDBC);
                 var prepareStatement = connection.prepareStatement(selectSql)) {
                prepareStatement.setString(1, product.getProductName());
                prepareStatement.setDouble(2, product.getPrice().doubleValue());
                prepareStatement.setInt(3, product.getCount());
                prepareStatement.setLong(4, product.getProductId());

                var rows = prepareStatement.executeUpdate();

                return rows > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    @Override
    public boolean deleteById(long id) {
        var selectSql = "DELETE FROM PRODUCTS where id = ?";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(selectSql)) {
            prepareStatement.setLong(1, id);
            var rows = prepareStatement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
}

}


