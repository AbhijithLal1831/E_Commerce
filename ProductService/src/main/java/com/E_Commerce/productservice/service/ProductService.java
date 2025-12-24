package com.E_Commerce.productservice.service;

import com.E_Commerce.productservice.requests.ProductRequest;
import com.E_Commerce.productservice.responses.ProductResponse;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

public interface ProductService {

  Page<ProductResponse> getAllProducts(int pageNumber, int pageSize);

  Optional<ProductResponse> getProductById(@NonNull String productId);

  ProductResponse createProduct(ProductRequest productRequest);

  Optional<ProductResponse> updateProduct(@NonNull String productId, ProductRequest productRequest);

  void deleteProduct(@NonNull String productId);
}
