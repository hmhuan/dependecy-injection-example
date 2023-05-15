package com.example.di.service;

import java.util.List;

import com.example.di.annotation.Component;
import com.example.di.annotation.Inject;
import com.example.di.entity.Product;
import com.example.di.repository.ProductRepository;

@Component
public class ProductService {
    @Inject
    protected ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.getProducts();
    }
}
