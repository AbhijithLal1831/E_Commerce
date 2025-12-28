package com.ecommerce.productservice.serviceimpl;

import com.ecommerce.productservice.entities.Category;
import com.ecommerce.productservice.entities.Product;
import com.ecommerce.productservice.mapper.ProductMapper;
import com.ecommerce.productservice.repositories.CategoryRepository;
import com.ecommerce.productservice.repositories.ProductRepository;
import com.ecommerce.productservice.requests.ProductRequest;
import com.ecommerce.productservice.exception.ResourceNotFoundException;
import com.ecommerce.productservice.responses.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductRequest productRequest;
    private ProductResponse productResponse;
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setCategoryName("Electronics");

        product = new Product();
        product.setProductId("123");
        product.setName("Laptop");
        product.setPrice(BigDecimal.valueOf(1000));
        product.setCategory(category);

        productRequest = new ProductRequest();
        productRequest.setName("Laptop");
        productRequest.setPrice(BigDecimal.valueOf(1000));
        productRequest.setCategoryName("Electronics");

        productResponse = new ProductResponse();
        productResponse.setProductId("123");
        productResponse.setName("Laptop");
        productResponse.setPrice(BigDecimal.valueOf(1000));
        productResponse.setCategoryName("Electronics");
    }

    @Test
    @SuppressWarnings("null")
    void createProduct_Success() {
        when(productMapper.toEntity(any(ProductRequest.class))).thenReturn(product);
        when(categoryRepository.findByCategoryName(anyString())).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toResponse(any(Product.class))).thenReturn(productResponse);

        ProductResponse response = productService.createProduct(productRequest);

        assertNotNull(response);
        assertEquals("Laptop", response.getName());
        verify(categoryRepository, times(1)).findByCategoryName("Electronics");
        verify(productRepository, times(1)).save(product);
    }

    @Test
    @SuppressWarnings("null")
    void createProduct_NewCategory_Success() {
        when(productMapper.toEntity(any(ProductRequest.class))).thenReturn(product);
        when(categoryRepository.findByCategoryName(anyString())).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(productRepository.save((Product) any(Product.class))).thenReturn(product);
        when(productMapper.toResponse(any(Product.class))).thenReturn(productResponse);

        ProductResponse response = productService.createProduct(productRequest);

        assertNotNull(response);
        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void getProductById_Success() {
        when(productRepository.findById("123")).thenReturn(Optional.of(product));
        when(productMapper.toResponse(product)).thenReturn(productResponse);

        ProductResponse result = productService.getProductById("123");

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
    }

    @Test
    void getProductById_NotFound() {
        when(productRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById("999"));
    }

    @Test
    @SuppressWarnings("null")
    void updateProduct_Success() {
        when(productRepository.findById("123")).thenReturn(Optional.of(product));
        when(categoryRepository.findByCategoryName(anyString())).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toResponse(any(Product.class))).thenReturn(productResponse);

        ProductResponse result = productService.updateProduct("123", productRequest);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    @SuppressWarnings("null")
    void updateProduct_NotFound() {
        when(productRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct("999", productRequest));

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void deleteProduct_Success() {
        doNothing().when(productRepository).deleteById("123");

        productService.deleteProduct("123");

        verify(productRepository, times(1)).deleteById("123");
    }

    @Test
    @SuppressWarnings("null")
    void getAllProducts_Success() {
        Page<Product> productPage = new PageImpl<>(Collections.singletonList(product));
        when(productRepository.findAll(any(PageRequest.class))).thenReturn(productPage);
        when(productMapper.toResponse(any(Product.class))).thenReturn(productResponse);

        Page<ProductResponse> result = productService.getAllProducts(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Laptop", result.getContent().get(0).getName());
    }
}
