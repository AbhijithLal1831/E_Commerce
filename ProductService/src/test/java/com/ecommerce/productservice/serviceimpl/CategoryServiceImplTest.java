package com.ecommerce.productservice.serviceimpl;

import com.ecommerce.productservice.entities.Category;
import com.ecommerce.productservice.exception.ResourceAlreadyExistsException;
import com.ecommerce.productservice.exception.ResourceNotFoundException;
import com.ecommerce.productservice.mapper.CategoryMapper;
import com.ecommerce.productservice.repositories.CategoryRepository;
import com.ecommerce.productservice.requests.CategoryRequest;
import com.ecommerce.productservice.responses.CategoryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryRequest categoryRequest;
    private CategoryResponse categoryResponse;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setCategoryId("cat123");
        category.setCategoryName("Electronics");

        categoryRequest = new CategoryRequest();
        categoryRequest.setCategoryName("Electronics");

        categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryId("cat123");
        categoryResponse.setCategoryName("Electronics");
    }

    @Test
    void getAllCategories_Success() {
        Page<Category> categoryPage = new PageImpl<>(Collections.singletonList(category));
        when(categoryRepository.findAll(any(PageRequest.class))).thenReturn(categoryPage);
        when(categoryMapper.toResponse(any(Category.class))).thenReturn(categoryResponse);

        Page<CategoryResponse> result = categoryService.getAllCategories(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Electronics", result.getContent().get(0).getCategoryName());
        verify(categoryRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void getCategoryById_Success() {
        when(categoryRepository.findById("cat123")).thenReturn(Optional.of(category));
        when(categoryMapper.toResponse(category)).thenReturn(categoryResponse);

        CategoryResponse result = categoryService.getCategoryById("cat123");

        assertNotNull(result);
        assertEquals("Electronics", result.getCategoryName());
        verify(categoryRepository, times(1)).findById("cat123");
    }

    @Test
    void getCategoryById_NotFound() {
        when(categoryRepository.findById("invalid")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById("invalid"));
        verify(categoryRepository, times(1)).findById("invalid");
    }

    @Test
    void createCategory_Success() {
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.empty());
        when(categoryMapper.toEntity(any(CategoryRequest.class))).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toResponse(any(Category.class))).thenReturn(categoryResponse);

        CategoryResponse result = categoryService.createCategory(categoryRequest);

        assertNotNull(result);
        assertEquals("Electronics", result.getCategoryName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void createCategory_AlreadyExists() {
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(Optional.of(category));

        assertThrows(ResourceAlreadyExistsException.class, () -> categoryService.createCategory(categoryRequest));
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void updateCategory_Success() {
        when(categoryRepository.findById("cat123")).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toResponse(any(Category.class))).thenReturn(categoryResponse);

        CategoryResponse result = categoryService.updateCategory("cat123", categoryRequest);

        assertNotNull(result);
        assertEquals("Electronics", result.getCategoryName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void updateCategory_NotFound() {
        when(categoryRepository.findById("invalid")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.updateCategory("invalid", categoryRequest));
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void deleteCategory_Success() {
        when(categoryRepository.existsById("cat123")).thenReturn(true);
        doNothing().when(categoryRepository).deleteById("cat123");

        categoryService.deleteCategory("cat123");

        verify(categoryRepository, times(1)).deleteById("cat123");
    }

    @Test
    void deleteCategory_NotFound() {
        when(categoryRepository.existsById("invalid")).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> categoryService.deleteCategory("invalid"));
        verify(categoryRepository, never()).deleteById("invalid");
    }
}
