package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.sber.model.Cart;
import ru.sber.model.Product;
import ru.sber.repository.CartRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/basket")
public class CartController {
    private final CartRepository basketRepository;

    public CartController(CartRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @PostMapping("/{basketId}")
    public ResponseEntity<?> addProductToBasket(@PathVariable long basketId, @RequestBody Product product) {
        boolean addedBasket = basketRepository.addProductToBasket(basketId, product.getProductId(), product.getCount());
        if (addedBasket) {
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(basketId)
                    .toUri();
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{basketId}")
    public ResponseEntity<List<Product>> getBasketProducts(@PathVariable long basketId) {
        Optional<Cart> optionalBasket = basketRepository.getBasketById(basketId);

        if (optionalBasket.isPresent()) {
            List<Product> products = optionalBasket.get().getProductList();
            return ResponseEntity.ok().body(products);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{basketId}/product/{productId}")
    public ResponseEntity<Cart> changeCountToBasket(@PathVariable long basketId, @PathVariable long productId,
                                                    @RequestParam int count) {
        Optional<Cart> changedBasket = basketRepository.changeProductCount(basketId, productId, count);
        if (changedBasket.isPresent()) {
            return ResponseEntity.ok(changedBasket.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("{basketId}")
    public ResponseEntity<?> deleteProductToBasket(@PathVariable long basketId, @RequestBody Product product) {
        Optional<Cart> optionalBasket = basketRepository.deleteProductFromBasket(basketId, product.getProductId());
        if (optionalBasket.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
