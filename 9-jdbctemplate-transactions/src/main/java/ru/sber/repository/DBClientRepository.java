package ru.sber.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sber.model.Client;
import java.sql.*;
import java.util.*;
/**
 * Класс, содержащий методы для работы с клиентом
 */
@Repository
public class DBClientRepository implements ClientRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DBClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long saveClient(Client client) {
        var insertClientSql = "INSERT INTO CLIENTS (name, username, password,email,cart_id) VALUES (?,?,?,?,?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareStatement = connection.prepareStatement(insertClientSql, Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setString(1, client.getClientName());
            prepareStatement.setString(2, client.getLogin());
            prepareStatement.setString(3, client.getPassword());
            prepareStatement.setString(4, client.getEmail());
            prepareStatement.setInt(5, saveClientCart());

            return prepareStatement;
        };
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return (long) (int) keyHolder.getKeys().get("id");
    }

    public int saveClientCart() {
        var insertSql = "INSERT INTO CARTS (promocode) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var prepareCartStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            prepareCartStatement.setString(1, "");
            return prepareCartStatement;
        };
        jdbcTemplate.update(preparedStatementCreator, keyHolder);
        return (int) keyHolder.getKeys().get("id");
    }

    @Override
    public List<Map<String, Object>> findClient(Long clientId) {
        String selectSql = "SELECT c.name AS clientName, c.email, c.cart_id AS clientBasket " +
                "FROM clients c " +
                "WHERE c.id = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
            preparedStatement.setLong(1, clientId);
            return preparedStatement;
        };

        RowMapper<Map<String, Object>> rowMapper = (resultSet, rowNum) -> {
            Map<String, Object> response = new HashMap<>();
            response.put("clientName", resultSet.getString("clientName"));
            response.put("email", resultSet.getString("email"));
            response.put("clientBasket", resultSet.getInt("clientBasket"));
            return response;
        };

        return jdbcTemplate.query(preparedStatementCreator, rowMapper);
    }


    @Override
    public   boolean deleteById(long clientId) {
        boolean productsCartsDeleted = deleteProductsCartsById(clientId);
        boolean cartDeleted = deleteCartById(clientId);
        boolean clientDeleted = deleteClientById(clientId);

        if (productsCartsDeleted && cartDeleted && clientDeleted) {
            return true;
        } else {
            return false;
        }
    }


    public boolean deleteProductsCartsById(long clientId) {
        String deleteProductsCartsSql = "DELETE FROM products_carts WHERE id_cart = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(deleteProductsCartsSql);
            preparedStatement.setLong(1, clientId);
            return preparedStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);

        return rows > 0;
    }

    public boolean deleteCartById(long clientId) {
        String deleteCartSql = "DELETE FROM carts WHERE id = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(deleteCartSql);
            preparedStatement.setLong(1, clientId);
            return preparedStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);

        return rows > 0;
    }

    public boolean deleteClientById(long clientId) {
        String deleteClientSql = "DELETE FROM clients WHERE id = ?";

        PreparedStatementCreator preparedStatementCreator = connection -> {
            var preparedStatement = connection.prepareStatement(deleteClientSql);
            preparedStatement.setLong(1, clientId);
            return preparedStatement;
        };

        int rows = jdbcTemplate.update(preparedStatementCreator);

        return rows > 0;
    }
}