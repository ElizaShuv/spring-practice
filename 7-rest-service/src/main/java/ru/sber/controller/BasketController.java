package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.model.Basket;
import ru.sber.model.Product;
import ru.sber.repository.BasketRepository;
import ru.sber.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/basket")
public class BasketController {

        private final BasketRepository basketRepository;
        private ProductRepository productRepository;

    public BasketController(BasketRepository basketRepository, ProductRepository productRepository) {
        this.basketRepository = basketRepository;
        this.productRepository = productRepository;
    }


    @PostMapping("/{basketId}")
    public ResponseEntity<String> addProductToBasket(@PathVariable long basketId, @RequestBody Product product) {
        Optional<Product> optionalProduct = productRepository.findById(product.getProductId());
        if (optionalProduct.isPresent()) {
            boolean added = basketRepository.addProductToBasket(basketId, product.getProductId());
            if (added) {
                return ResponseEntity.ok("Продукт " + product.getProductId() + " добавлен в корзину");
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.badRequest().body("Продукт с ID " + product.getProductId() + " не найден");
        }
    }

    @GetMapping("/{basketId}")
    public ResponseEntity<List<Product>> getBasketProducts(@PathVariable long basketId) {
        Optional<Basket> optionalBasket = basketRepository.getBasketById(basketId);

        if (optionalBasket.isPresent()) {
            List<Product> products = optionalBasket.get().getProductList();
            return ResponseEntity.ok().body(products);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
