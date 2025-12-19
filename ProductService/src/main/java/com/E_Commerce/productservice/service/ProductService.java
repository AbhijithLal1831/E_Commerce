package com.E_Commerce.productservice.service;

import com.E_Commerce.productservice.entities.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ProductService {

  Page<Product> getAllProducts(int pageNumber, int pageSize);

  Optional<Product> getProductById(String productId);
}
