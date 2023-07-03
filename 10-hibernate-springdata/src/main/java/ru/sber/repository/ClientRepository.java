package ru.sber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sber.model.Client;

import java.util.List;
import java.util.Map;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT new map(c.clientName as clientName, c.email as email, c.clientBasket.cartId as clientBasket) FROM Client c WHERE c.clientId = :clientId")
    List<Map<String, Object>> findClient(@Param("clientId") Long clientId);
}
