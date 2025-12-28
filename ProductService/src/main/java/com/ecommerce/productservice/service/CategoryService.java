package com.ecommerce.productservice.service;

import com.ecommerce.productservice.requests.CategoryRequest;
import com.ecommerce.productservice.responses.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;

public interface CategoryService {

    Page<CategoryResponse> getAllCategories(int pageNumber, int pageSize);

    CategoryResponse getCategoryById(@NonNull String categoryId);

    CategoryResponse createCategory(CategoryRequest categoryRequest);

    CategoryResponse updateCategory(@NonNull String categoryId, CategoryRequest categoryRequest);

    void deleteCategory(@NonNull String categoryId);
}
