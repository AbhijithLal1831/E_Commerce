package com.E_Commerce.productservice.serviceimpl;

import com.E_Commerce.productservice.entities.Category;
import com.E_Commerce.productservice.entities.Product;
import com.E_Commerce.productservice.mapper.ProductMapper;
import com.E_Commerce.productservice.repositories.CategoryRepository;
import com.E_Commerce.productservice.repositories.ProductRepository;
import com.E_Commerce.productservice.requests.ProductRequest;
import com.E_Commerce.productservice.responses.ProductResponse;
import com.E_Commerce.productservice.service.ProductService;
import com.E_Commerce.productservice.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final ProductMapper productMapper;

  public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
      ProductMapper productMapper) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
    this.productMapper = productMapper;
  }

  @Override
  public Page<ProductResponse> getAllProducts(int pageNumber, int pageSize) {
    log.info("Fetching products page. PageNumber: {}, PageSize: {}", pageNumber, pageSize);
    return productRepository.findAll(PageRequest.of(pageNumber, pageSize))
        .map(productMapper::toResponse);
  }

  @Override
  public ProductResponse getProductById(@NonNull String productId) {
    log.info("Fetching product by ID: {}", productId);
    return productRepository.findById(productId)
        .map(productMapper::toResponse)
        .orElseThrow(() -> {
          log.error("Product not found with ID: {}", productId);
          return new ResourceNotFoundException("Product", "id", productId);
        });
  }

  @Override
  public ProductResponse createProduct(ProductRequest productRequest) {
    log.info("Creating product: {}", productRequest.getName());
    Product product = productMapper.toEntity(productRequest);

    Category category = getOrCreateCategory(productRequest.getCategoryName());
    product.setCategory(category);

    Product savedProduct = productRepository.save(product);
    log.info("Product created successfully with ID: {}", savedProduct.getProductId());
    return productMapper.toResponse(savedProduct);
  }

  @Override
  public ProductResponse updateProduct(@NonNull String productId, ProductRequest productRequest) {
    log.info("Updating product with ID: {}", productId);
    Product existingProduct = productRepository.findById(productId)
        .orElseThrow(() -> {
          log.error("Cannot update. Product not found with ID: {}", productId);
          return new ResourceNotFoundException("Product", "id", productId);
        });

    existingProduct.setName(productRequest.getName());
    existingProduct.setDescription(productRequest.getDescription());
    existingProduct.setPrice(productRequest.getPrice());

    Category category = getOrCreateCategory(productRequest.getCategoryName());
    existingProduct.setCategory(category);

    Product savedProduct = productRepository.save(existingProduct);
    log.info("Product updated successfully: {}", savedProduct.getProductId());
    return productMapper.toResponse(savedProduct);
  }

  @Override
  public void deleteProduct(@NonNull String productId) {
    log.info("Deleting product with ID: {}", productId);
    productRepository.deleteById(productId);
  }

  private Category getOrCreateCategory(String categoryName) {
    if (categoryName == null || categoryName.isBlank()) {
      return null;
    }
    return categoryRepository.findByCategoryName(categoryName)
        .orElseGet(() -> {
          log.info("Category '{}' not found, creating new one.", categoryName);
          Category newCategory = new Category();
          newCategory.setCategoryName(categoryName);
          return categoryRepository.save(newCategory);
        });
  }
}
