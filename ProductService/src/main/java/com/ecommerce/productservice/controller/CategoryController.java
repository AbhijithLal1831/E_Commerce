package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.requests.CategoryRequest;
import com.ecommerce.productservice.responses.CategoryResponse;
import com.ecommerce.productservice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("v1/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(path = "/listCategories", name = "get-all-categories")
    public Page<CategoryResponse> getAllCategories(@RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "20") int pageSize) {
        log.info("Request received to fetch all categories. PageNumber: {}, PageSize: {}", pageNumber, pageSize);
        return categoryService.getAllCategories(pageNumber, pageSize);
    }

    @GetMapping(path = "/getCategoryById")
    public CategoryResponse getCategoryById(@RequestParam @NonNull String categoryId) {
        log.info("Request received to fetch category by ID: {}", categoryId);
        return categoryService.getCategoryById(categoryId);
    }

    @PostMapping("/create")
    public CategoryResponse createCategory(
            @Valid @RequestBody CategoryRequest categoryRequest) {
        log.info("Request received to create category: {}", categoryRequest.getCategoryName());
        return categoryService.createCategory(categoryRequest);
    }

    @PutMapping("/update/{categoryId}")
    public CategoryResponse updateCategory(@PathVariable @NonNull String categoryId,
            @Valid @RequestBody CategoryRequest categoryRequest) {
        log.info("Request received to update category with ID: {}", categoryId);
        return categoryService.updateCategory(categoryId, categoryRequest);
    }

    @DeleteMapping("/delete/{categoryId}")
    public void deleteCategory(@PathVariable @NonNull String categoryId) {
        log.info("Request received to delete category with ID: {}", categoryId);
        categoryService.deleteCategory(categoryId);
    }
}
