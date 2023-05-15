package com.example.di.repository;

import java.util.List;

import com.example.di.annotation.Component;
import com.example.di.annotation.Inject;
import com.example.di.dbengine.ConsoleDBManager;
import com.example.di.entity.Product;

@Component
public class ProductRepository {
    @Inject
    protected ConsoleDBManager consoleDBManager;

    public List<Product> getProducts() {
        return consoleDBManager.query(Product.class);
    }
}
