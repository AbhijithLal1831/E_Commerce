package com.E_Commerce.productservice.serviceimpl;

import com.E_Commerce.productservice.entities.Product;
import com.E_Commerce.productservice.repositories.ProductRepository;
import com.E_Commerce.productservice.service.ProductService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }


  @Override
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }
}
