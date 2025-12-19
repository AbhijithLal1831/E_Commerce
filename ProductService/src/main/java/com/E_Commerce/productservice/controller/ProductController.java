package com.E_Commerce.productservice.controller;

import com.E_Commerce.productservice.entities.Product;
import com.E_Commerce.productservice.service.ProductService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/products")
@AllArgsConstructor
public class ProductController {

  private ProductService productService;

  @GetMapping(path = "/listProducts", name = "get-all-products")
  public Page<Product> getAllProducts(@RequestParam(defaultValue = "0") int pageNumber,
      @RequestParam(defaultValue = "20") int pageSize) {
    return productService.getAllProducts(pageNumber, pageSize);
  }

  @GetMapping(path = "/getProductById")
  public Optional<Product> getProductById(@RequestParam String productId) {
    return productService.getProductById(productId);
  }
}
