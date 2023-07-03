package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.sber.model.Cart;
import ru.sber.model.Product;
import ru.sber.model.ProductCart;
import ru.sber.service.CartService;
import ru.sber.service.CartServiceInterface;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@RestController
@RequestMapping("/basket")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{basketId}")
    public ResponseEntity<?> addProductToBasket(@PathVariable long basketId, @RequestBody Product product) {
        int count = product.getCount();
        boolean addedBasket = cartService.addProductToBasket(basketId, product.getProductId(), count);
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
    public ResponseEntity<List<ProductCart>> getBasketProducts(@PathVariable long basketId) {
        Optional<Cart> optionalBasket = cartService.getBasketById(basketId);

        if (optionalBasket.isPresent()) {
            List<ProductCart> productCarts = optionalBasket.get().getProductCarts();
            return ResponseEntity.ok(productCarts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{basketId}/product/{productId}")
    public ResponseEntity<Cart> changeCountToBasket(@PathVariable long basketId, @PathVariable long productId,
                                                    @RequestParam int count) {
        Optional<Cart> changedBasket = cartService.changeProductCount(basketId, productId, count);
        if (changedBasket.isPresent()) {
            return ResponseEntity.ok(changedBasket.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{basketId}/product/{productId}")
    public ResponseEntity<?> deleteProductFromBasket(@PathVariable long basketId, @PathVariable long productId) {
        Optional<Cart> optionalBasket = cartService.deleteProductFromBasket(basketId, productId);
        if (optionalBasket.isPresent()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
