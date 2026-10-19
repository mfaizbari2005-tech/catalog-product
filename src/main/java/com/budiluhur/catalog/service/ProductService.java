package com.budiluhur.catalog.service;

import org.springframework.stereotype.Service;
import com.budiluhur.catalog.model.Product;
import com.budiluhur.catalog.repository.ProductRepository;
import com.budiluhur.catalog.exception.ProductNotFoundException;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final DiscountService discountService; // Mini Challenge 4

    // Constructor Injection untuk IoC
    public ProductService(ProductRepository productRepository, DiscountService discountService) {
        this.productRepository = productRepository;
        this.discountService = discountService;
    }

    public List<Product> getAllProducts() { return productRepository.findAll(); }
    
    public Product getProductById(String id) throws ProductNotFoundException {
        return productRepository.findById(id);
    }
    
    public Product createProduct(Product product) {
        productRepository.addProduct(product);
        return product;
    }

    public Product updateProduct(String id, Product product) throws ProductNotFoundException {
        return productRepository.updateProduct(id, product);
    }

    public boolean deleteProduct(String id) throws ProductNotFoundException {
        return productRepository.deleteById(id);
    }

    // Mini Challenge 4: Hitung Diskon
    public double getDiscountedProductPrice(String id, double discount) throws ProductNotFoundException {
        Product product = getProductById(id);
        return discountService.calculateDiscountedPrice(product.getPrice(), discount);
    }

    // Mini Challenge 5: Fitur Pencarian 
    public List<Product> searchProducts(String keyword) {
        return productRepository.findAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }
}