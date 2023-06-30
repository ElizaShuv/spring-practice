package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.sber.model.Basket;
import ru.sber.model.Product;
import ru.sber.repository.BasketRepository;
import ru.sber.repository.ProductRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/basket")
public class BasketController {
    private final BasketRepository basketRepository;

    public BasketController(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @PostMapping("/{basketId}")
    public ResponseEntity<Basket> addProductToBasket(@PathVariable long basketId, @RequestBody Product product) {
        Optional<Basket> addedBasket = basketRepository.addProductToBasket(basketId, product.getProductId());
        if (addedBasket.isPresent()) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(addedBasket.get().getBasketId())
                    .toUri();
            return ResponseEntity.created(location).body(addedBasket.get());
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
    public ResponseEntity<?> deleteProductToBasket(@PathVariable long basketId, @RequestBody Product product) {
        Optional<Basket> optionalBasket = basketRepository.deleteProductToBasket(basketId, product.getProductId());
        if (optionalBasket.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
