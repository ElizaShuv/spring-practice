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
    public ResponseEntity<Basket> addProductToBasket(@PathVariable long basketId, @RequestBody Product product) {
        Optional<Basket> addedBasket = basketRepository.addProductToBasket(basketId, product.getProductId());
        if (addedBasket.isPresent()) {
            return ResponseEntity.ok(addedBasket.get());
        } else {
            return ResponseEntity.notFound().build();
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

    @PutMapping("/{basketId}/product/{productId}")
    public ResponseEntity<Basket> changeCountToBasket(@PathVariable long basketId, @PathVariable long productId,
                                                      @RequestParam int count) {
        Optional<Basket> changedBasket = basketRepository.changeProductCount(basketId, productId, count);
        if (changedBasket.isPresent()) {
            return ResponseEntity.ok(changedBasket.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("{basketId}")
    public ResponseEntity<Basket> deleteProductToBasket(@PathVariable long basketId, @RequestBody Product product) {
        Optional<Basket> addedBasket = basketRepository.deleteProductToBasket(basketId, product.getProductId());
        if (addedBasket.isPresent()) {
            return ResponseEntity.ok(addedBasket.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping("/{cartId}/payment")
//    public PayCard processPayment(@PathVariable long basketId) {
//        return basketRepository.payment(cartId);
//    }
}
