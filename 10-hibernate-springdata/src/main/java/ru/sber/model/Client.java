package ru.sber.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Клиент
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long clientId;

    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private Cart clientBasket;
}
