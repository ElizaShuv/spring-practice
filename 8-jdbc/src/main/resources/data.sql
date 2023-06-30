create schema if not exists products_shuvalova_eu;

create table products_shuvalova_eu.products
(
    id    integer generated always as identity primary key,
    name  varchar(255) not null,
    price numeric      not null,
    count integer      not null
    );

create table products_shuvalova_eu.carts
(
    id        integer generated always as identity primary key,
    promocode varchar(255)
    );

create table products_shuvalova_eu.clients
(
    id       integer generated always as identity primary key,
    name     varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    email    varchar(255),
    cart_id  integer      not null
    constraint client_cart_id_fk
    references products_shuvalova_eu.carts
    );

create table products_shuvalova_eu.products_carts
(
    id         integer generated always as identity primary key,
    id_product integer not null
    constraint product_client_product_id_fk
    references products_shuvalova_eu.products,
    id_cart    integer not null
    constraint product_client_cart_id_fk
    references products_shuvalova_eu.carts,
    count      integer not null
);