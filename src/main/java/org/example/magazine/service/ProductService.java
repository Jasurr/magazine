package org.example.magazine.service;

import org.example.magazine.model.Product;
import org.example.magazine.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product getOne(Long productId) {
        return productRepository.findById(productId).get();
    }

    public void delete(Long productId) {
        productRepository.deleteById(productId);
    }

}

