package ru.sber.repository;

import org.springframework.stereotype.Repository;
import ru.sber.model.Client;
import java.sql.*;
import java.util.*;
/**
 * Класс, содержащий методы для работы с клиентом
 */
@Repository
public class BDClientRepository implements ClientRepository {

    public static final String JDBC = "jdbc:postgresql://localhost:5432/postgres?currentSchema=products_shuvalova_eu&user=postgres&password=12345";

    @Override
    public long saveClient(Client client) {
        var insertClientSql = "INSERT INTO CLIENTS (name, username, password,email,cart_id) VALUES (?,?,?,?,?);";

        try (var connection = DriverManager.getConnection(JDBC);
             var prepareStatement = connection.prepareStatement(insertClientSql, Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setString(1, client.getClientName());
            prepareStatement.setString(2, client.getLogin());
            prepareStatement.setString(3, client.getPassword());
            prepareStatement.setString(4, client.getEmail());
            prepareStatement.setInt(5, saveClientCart());

            prepareStatement.executeUpdate();

            ResultSet rs = prepareStatement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора клиента");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int saveClientCart() {
        var insertSql = "INSERT INTO CARTS (promocode) VALUES (?)";
        try {
            var connection = DriverManager.getConnection(JDBC);
            var prepareCartStatement = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            prepareCartStatement.setString(1, "");
            prepareCartStatement.executeUpdate();
            ResultSet cartKeys = prepareCartStatement.getGeneratedKeys();

            if (cartKeys.next()) {
                return cartKeys.getInt(1);
            } else {
                throw new RuntimeException("Ошибка при получении идентификатора корзины");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Map<String, Object>> findClient(Long clientId) {
        String selectSql = "SELECT c.name AS clientName, c.email, c.cart_id AS clientBasket " +
                "FROM clients c " +
                "WHERE c.id = ?";

        try (Connection connection = DriverManager.getConnection(JDBC);
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setObject(1, clientId);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Map<String, Object>> clientResponses = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, Object> response = new HashMap<>();
                response.put("clientName", resultSet.getString("clientName"));
                response.put("email", resultSet.getString("email"));
                response.put("clientBasket", resultSet.getInt("clientBasket"));
                clientResponses.add(response);
            }

            return clientResponses;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
@Override
public boolean deleteById(long clientId) {
    String deleteProductsCartsSql = "DELETE FROM products_carts WHERE id_cart = ?";
    String deleteCartSql = "DELETE FROM carts WHERE id = ?";
    String deleteClientSql = "DELETE FROM clients WHERE id = ?";

    try (var connection = DriverManager.getConnection(JDBC);
         PreparedStatement deleteProductsCartsStatement = connection.prepareStatement(deleteProductsCartsSql);
         PreparedStatement deleteCartStatement = connection.prepareStatement(deleteCartSql);
         PreparedStatement deleteClientStatement = connection.prepareStatement(deleteClientSql)) {


        deleteClientStatement.setLong(1, clientId);
        deleteProductsCartsStatement.setLong(1, clientId);
        deleteCartStatement.setLong(1, clientId);

        connection.setAutoCommit(false);

        deleteClientStatement.executeUpdate();
        deleteProductsCartsStatement.executeUpdate();

        int clientRowsAffected =    deleteCartStatement.executeUpdate();

        if (clientRowsAffected > 0) {
            connection.commit();
            return true;
        } else {
            connection.rollback();
            return false;
        }

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

}