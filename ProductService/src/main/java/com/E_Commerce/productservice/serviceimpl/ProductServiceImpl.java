package com.E_Commerce.productservice.serviceimpl;

import com.E_Commerce.productservice.entities.Category;
import com.E_Commerce.productservice.entities.Product;
import com.E_Commerce.productservice.mapper.ProductMapper;
import com.E_Commerce.productservice.repositories.CategoryRepository;
import com.E_Commerce.productservice.repositories.ProductRepository;
import com.E_Commerce.productservice.requests.ProductRequest;
import com.E_Commerce.productservice.responses.ProductResponse;
import com.E_Commerce.productservice.service.ProductService;
import java.util.Optional;
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
    return productRepository.findAll(PageRequest.of(pageNumber, pageSize))
        .map(productMapper::toResponse);
  }

  @Override
  public Optional<ProductResponse> getProductById(@NonNull String productId) {
    return productRepository.findById(productId)
        .map(productMapper::toResponse);
  }

  @Override
  public ProductResponse createProduct(ProductRequest productRequest) {
    Product product = productMapper.toEntity(productRequest);

    Category category = getOrCreateCategory(productRequest.getCategoryName());
    product.setCategory(category);

    Product savedProduct = productRepository.save(product);
    return productMapper.toResponse(savedProduct);
  }

  @Override
  public Optional<ProductResponse> updateProduct(@NonNull String productId, ProductRequest productRequest) {
    Optional<Product> existingProductOpt = productRepository.findById(productId);
    if (existingProductOpt.isPresent()) {
      Product existingProduct = existingProductOpt.get();
      existingProduct.setName(productRequest.getName());
      existingProduct.setDescription(productRequest.getDescription());
      existingProduct.setPrice(productRequest.getPrice());

      Category category = getOrCreateCategory(productRequest.getCategoryName());
      existingProduct.setCategory(category);

      Product savedProduct = productRepository.save(existingProduct);
      return Optional.of(productMapper.toResponse(savedProduct));
    }
    return Optional.empty();
  }

  @Override
  public void deleteProduct(@NonNull String productId) {
    productRepository.deleteById(productId);
  }

  private Category getOrCreateCategory(String categoryName) {
    if (categoryName == null || categoryName.isBlank()) {
      return null;
    }
    return categoryRepository.findByCategoryName(categoryName)
        .orElseGet(() -> {
          Category newCategory = new Category();
          newCategory.setCategoryName(categoryName);
          return categoryRepository.save(newCategory);
        });
  }
}
