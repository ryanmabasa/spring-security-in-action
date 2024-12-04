package com.example.ch12.controller;

import com.example.ch12.entity.Product;
import com.example.ch12.entity.ProductEntity;
import com.example.ch12.entity.ProductRepository;
import com.example.ch12.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    // omitted constructor


    @GetMapping("/sell")
    public List<Product> sellProduct() {
        List<Product> products = new ArrayList<>();

        products.add(new Product("beer", "nikolai"));
        products.add(new Product("candy", "nikolai"));
        products.add(new Product("chocolate", "julien"));

        return productService.sellProducts(products);
    }

    @GetMapping("/find")
    public List<Product> findProducts() {
        return productService.findProducts();
    }


    // Omitted constructor

    @GetMapping("/products/{text}")
    public List<ProductEntity> findProductsContaining(@PathVariable String text) {

        return productRepository.findProductByNameContains(text);
    }
}
