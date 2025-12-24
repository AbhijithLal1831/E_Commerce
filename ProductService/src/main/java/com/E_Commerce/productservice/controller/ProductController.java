package com.E_Commerce.productservice.controller;

import com.E_Commerce.productservice.requests.ProductRequest;
import com.E_Commerce.productservice.responses.ProductResponse;
import com.E_Commerce.productservice.service.ProductService;

import jakarta.validation.Valid;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/products")
@AllArgsConstructor
public class ProductController {

  private ProductService productService;

  @GetMapping(path = "/listProducts", name = "get-all-products")
  public Page<ProductResponse> getAllProducts(@RequestParam(defaultValue = "0") int pageNumber,
      @RequestParam(defaultValue = "20") int pageSize) {
    return productService.getAllProducts(pageNumber, pageSize);
  }

  @PostMapping("/create")
  public ProductResponse createProduct(
      @Valid @RequestBody ProductRequest productRequest) {
    return productService.createProduct(productRequest);
  }

  @PutMapping("/update/{productId}")
  public Optional<ProductResponse> updateProduct(@PathVariable @NonNull String productId,
      @Valid @RequestBody ProductRequest productRequest) {
    return productService.updateProduct(productId, productRequest);
  }

  @DeleteMapping("/delete/{productId}")
  public void deleteProduct(@PathVariable @NonNull String productId) {
    productService.deleteProduct(productId);
  }

  @GetMapping(path = "/getProductById")
  public Optional<ProductResponse> getProductById(@RequestParam @NonNull String productId) {
    return productService.getProductById(productId);
  }
}
