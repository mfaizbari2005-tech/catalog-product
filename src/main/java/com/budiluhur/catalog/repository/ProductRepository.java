package com.budiluhur.catalog.repository;

import org.springframework.stereotype.Repository;
import com.budiluhur.catalog.model.Product;
import com.budiluhur.catalog.exception.ProductNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    private final List<Product> productList = new ArrayList<>();

    public ProductRepository() {
        productList.add(new Product("PRD-01", "Keyboard Mechanical", 450000.0));
        productList.add(new Product("PRD-02", "Mouse Wireless Silent", 175000.0));
        productList.add(new Product("PRD-03", "Monitor Gaming 24 Inch", 2100000.0));
    }

    public void addProduct(Product product) { productList.add(product); }
    public List<Product> findAll() { return productList; }

    public Product findById(String id) throws ProductNotFoundException {
        for (Product p : productList) {
            if (p.getId().equalsIgnoreCase(id)) return p;
        }
        throw new ProductNotFoundException("Produk dengan ID '" + id + "' tidak ditemukan!");
    }

    public Product updateProduct(String id, Product updatedProduct) throws ProductNotFoundException {
        Product existingProduct = findById(id);
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        return existingProduct;
    }

    public boolean deleteById(String id) throws ProductNotFoundException {
        Product existingProduct = findById(id);
        return productList.remove(existingProduct);
    }
}