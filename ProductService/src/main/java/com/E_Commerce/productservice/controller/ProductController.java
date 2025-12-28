package com.E_Commerce.productservice.controller;

import lombok.extern.slf4j.Slf4j;

import com.E_Commerce.productservice.requests.ProductRequest;
import com.E_Commerce.productservice.responses.ProductResponse;
import com.E_Commerce.productservice.service.ProductService;

import jakarta.validation.Valid;

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

@Slf4j
@RestController
@RequestMapping("v1/products")
@AllArgsConstructor
public class ProductController {

  private ProductService productService;

  @GetMapping(path = "/listProducts", name = "get-all-products")
  public Page<ProductResponse> getAllProducts(@RequestParam(defaultValue = "0") int pageNumber,
      @RequestParam(defaultValue = "20") int pageSize) {
    log.info("Request received to fetch all products. PageNumber: {}, PageSize: {}", pageNumber, pageSize);
    return productService.getAllProducts(pageNumber, pageSize);
  }

  @PostMapping("/create")
  public ProductResponse createProduct(
      @Valid @RequestBody ProductRequest productRequest) {
    log.info("Request received to create product: {}", productRequest.getName());
    return productService.createProduct(productRequest);
  }

  @PutMapping("/update/{productId}")
  public ProductResponse updateProduct(@PathVariable @NonNull String productId,
      @Valid @RequestBody ProductRequest productRequest) {
    log.info("Request received to update product with ID: {}", productId);
    return productService.updateProduct(productId, productRequest);
  }

  @DeleteMapping("/delete/{productId}")
  public void deleteProduct(@PathVariable @NonNull String productId) {
    log.info("Request received to delete product with ID: {}", productId);
    productService.deleteProduct(productId);
  }

  @GetMapping(path = "/getProductById")
  public ProductResponse getProductById(@RequestParam @NonNull String productId) {
    log.info("Request received to fetch product by ID: {}", productId);
    return productService.getProductById(productId);
  }
}
