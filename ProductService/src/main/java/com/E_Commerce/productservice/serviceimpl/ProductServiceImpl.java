package com.E_Commerce.productservice.serviceimpl;

import com.E_Commerce.productservice.entities.Product;
import com.E_Commerce.productservice.repositories.ProductRepository;
import com.E_Commerce.productservice.service.ProductService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }


  @Override
  public Page<Product> getAllProducts(int pageNumber, int pageSize) {
    return productRepository.findAll(PageRequest.of(pageNumber, pageSize));
  }

  @Override
  public Optional<Product> getProductById(String productId) {
    return productRepository.findById(productId);
  }
}
