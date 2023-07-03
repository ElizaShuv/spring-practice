package ru.sber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    }
