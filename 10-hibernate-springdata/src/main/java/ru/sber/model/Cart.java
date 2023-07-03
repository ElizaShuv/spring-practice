package ru.sber.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
/**
 * Корзина
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;

    @Column
    private String promocode;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductCart> productCarts = new ArrayList<>();
    public void addProductCart(ProductCart productCart) {
        productCarts.add(productCart);
        productCart.setCart(this);
    }
}

