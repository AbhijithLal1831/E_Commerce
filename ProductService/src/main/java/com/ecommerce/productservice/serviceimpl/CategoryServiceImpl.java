package com.ecommerce.productservice.serviceimpl;

import com.ecommerce.productservice.entities.Category;
import com.ecommerce.productservice.exception.ResourceNotFoundException;
import com.ecommerce.productservice.exception.ResourceAlreadyExistsException;
import com.ecommerce.productservice.mapper.CategoryMapper;
import com.ecommerce.productservice.repositories.CategoryRepository;
import com.ecommerce.productservice.requests.CategoryRequest;
import com.ecommerce.productservice.responses.CategoryResponse;
import com.ecommerce.productservice.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Page<CategoryResponse> getAllCategories(int pageNumber, int pageSize) {
        log.info("Fetching categories page. PageNumber: {}, PageSize: {}", pageNumber, pageSize);
        return categoryRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(categoryMapper::toResponse);
    }

    @Override
    public CategoryResponse getCategoryById(@NonNull String categoryId) {
        log.info("Fetching category by ID: {}", categoryId);
        return categoryRepository.findById(categoryId)
                .map(categoryMapper::toResponse)
                .orElseThrow(() -> {
                    log.error("Category not found with ID: {}", categoryId);
                    return new ResourceNotFoundException("Category", "id", categoryId);
                });
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        log.info("Creating category: {}", categoryRequest.getCategoryName());
        categoryRepository.findByCategoryName(categoryRequest.getCategoryName()).ifPresent(existing -> {
            log.error("Category already exists with name: {}", existing.getCategoryName());
            throw new ResourceAlreadyExistsException("Category", "categoryName", existing.getCategoryName(),
                    existing.getCategoryId());
        });

        Category category = categoryMapper.toEntity(categoryRequest);
        Category savedCategory = categoryRepository.save(category);
        log.info("Category created successfully with ID: {}", savedCategory.getCategoryId());
        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponse updateCategory(@NonNull String categoryId, CategoryRequest categoryRequest) {
        log.info("Updating category with ID: {}", categoryId);
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("Cannot update. Category not found with ID: {}", categoryId);
                    return new ResourceNotFoundException("Category", "id", categoryId);
                });

        existingCategory.setCategoryName(categoryRequest.getCategoryName());

        Category savedCategory = categoryRepository.save(existingCategory);
        log.info("Category updated successfully: {}", savedCategory.getCategoryId());
        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public void deleteCategory(@NonNull String categoryId) {
        log.info("Deleting category with ID: {}", categoryId);
        if (!categoryRepository.existsById(categoryId)) {
            log.error("Cannot delete. Category not found with ID: {}", categoryId);
            throw new ResourceNotFoundException("Category", "id", categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }
}
