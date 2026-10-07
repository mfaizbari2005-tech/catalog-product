package com.budiluhur.catalog.repository;

import com.budiluhur.catalog.exception.ProductNotFoundException;
import com.budiluhur.catalog.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private List<Product> productList = new ArrayList<>();

    public void addProduct(Product product) {
        productList.add(product);
    }

    public List<Product> findAll() {
        return productList;
    }

    public Product findById(String id) throws ProductNotFoundException {
        for (Product p : productList) {
            if (p.getId().equalsIgnoreCase(id)) {
                return p;
            }
        }
        throw new ProductNotFoundException("Produk dengan ID '" + id + "' tidak ditemukan!");
    }

    // Method Mini Challenge (Slide 23)
    public boolean deleteById(String id) throws ProductNotFoundException {
        for (Product p : productList) {
            if (p.getId().equalsIgnoreCase(id)) {
                productList.remove(p);
                return true;
            }
        }
        throw new ProductNotFoundException("Gagal menghapus! Produk dengan ID '" + id + "' tidak ditemukan!");
    }
}