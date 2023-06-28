package ru.sber.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.model.Product;
import ru.sber.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("products")
public class ProductController {
    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public long addProduct(@RequestBody Product product) {
        log.info("Добавление продукта {}", product);

        return productRepository.save(product);
    }

    @GetMapping
    public List<Product> getProducts(@RequestParam(required = false) String name) {
        log.info("Поиск продуктов по имени {}", name);

        return productRepository.findAll(name);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProducts(@PathVariable long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            return ResponseEntity.ok().body(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        productRepository.update(product);

        return product;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        boolean isDeleted = productRepository.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
