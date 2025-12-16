package com.E_Commerce.productservice.controller;

import com.E_Commerce.productservice.entities.Product;
import com.E_Commerce.productservice.service.ProductService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/products")
public class ProductController {

  private ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping(path = "/listProducts", name = "get-all-products")
  public List<Product> getAllProducts() {
    return productService.getAllProducts();
  }
}
