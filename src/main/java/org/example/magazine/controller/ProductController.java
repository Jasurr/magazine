package org.example.magazine.controller;

import org.example.magazine.model.Product;
import org.example.magazine.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("{productId}")
    public ResponseEntity<Product> getOne(@RequestParam("productId") Long productId) {
        return ResponseEntity.ok(productService.getOne(productId));
    }

    @PostMapping
    public ResponseEntity<Product> add(@RequestBody Product product) {
        try {
            productService.save(product);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<Product> update(@RequestBody Product product) {
        try {
            if (product.getId() == null) {
                throw new Exception("productId must be required");
            }
            Product oldProduct = productService.getOne(product.getId());
            oldProduct.setName(product.getName());
            oldProduct.setDescription(product.getDescription());
            oldProduct.setStock(product.getStock());
            oldProduct.setPrice(product.getPrice());
            productService.save(oldProduct);
            return new ResponseEntity<>(oldProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{productId}")
    public void delete(@RequestParam("productId") Long productId) {
        productService.delete(productId);
    }
}
