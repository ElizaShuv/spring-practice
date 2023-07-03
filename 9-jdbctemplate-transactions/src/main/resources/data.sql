INSERT INTO products_shuvalova_eu.products (name, price, count)
VALUES
    ('Ананас', 300, 10),
    ('Манго', 200, 20),
    ('Апельсин', 100, 30);

INSERT INTO products_shuvalova_eu.carts (promocode)
VALUES (''), (''), ('');

-- 1-й клиент для удачной оплаты
-- 2-й клиент для ошибки "Недостаточно продуктов"
-- 3-й клиент для ошибки "Недостаточно средств"
INSERT INTO products_shuvalova_eu.clients (name, username, password, email, cart_id)
VALUES ('Иван', 'ivan', '12345', 'ivan@gmail.com', 1),
       ('Петр', 'petr', '54321', 'petr@gmail.com', 2),
       ('Анна', 'anna', '11111', 'anna@gmail.com', 3);

INSERT INTO products_shuvalova_eu.products_carts (id_product, id_cart, count)
VALUES
    (1, 1, 1),
    (2, 1, 2),
    (1, 2, 11),
    (3, 3, 20),
    (1, 3, 3);

