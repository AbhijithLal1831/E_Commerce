package com.ecommerce.productservice.service;

import com.ecommerce.productservice.requests.ProductRequest;
import com.ecommerce.productservice.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

public interface ProductService {

  Page<ProductResponse> getAllProducts(int pageNumber, int pageSize);

  ProductResponse getProductById(@NonNull String productId);

  ProductResponse createProduct(ProductRequest productRequest);

  ProductResponse updateProduct(@NonNull String productId, ProductRequest productRequest);

  void deleteProduct(@NonNull String productId);
}
