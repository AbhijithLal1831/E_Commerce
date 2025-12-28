package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.exception.ResourceNotFoundException;
import com.ecommerce.productservice.requests.CategoryRequest;
import com.ecommerce.productservice.responses.CategoryResponse;
import com.ecommerce.productservice.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoryRequest categoryRequest;
    private CategoryResponse categoryResponse;

    @BeforeEach
    void setUp() {
        categoryRequest = new CategoryRequest();
        categoryRequest.setCategoryName("Test Category");

        categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryId("cat-123");
        categoryResponse.setCategoryName("Test Category");
    }

    @Test
    void createCategory_Success() throws Exception {
        when(categoryService.createCategory(any(CategoryRequest.class))).thenReturn(categoryResponse);

        mockMvc.perform(post("/v1/categories/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value("cat-123"))
                .andExpect(jsonPath("$.categoryName").value("Test Category"));
    }

    @Test
    void createCategory_ValidationFailure() throws Exception {
        categoryRequest.setCategoryName(""); // Invalid: Blank

        mockMvc.perform(post("/v1/categories/create")
                .contentType(Objects.requireNonNull(MediaType.APPLICATION_JSON))
                .content(Objects.requireNonNull(objectMapper.writeValueAsString(categoryRequest))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCategoryById_Success() throws Exception {
        when(categoryService.getCategoryById("cat-123")).thenReturn(categoryResponse);

        mockMvc.perform(get("/v1/categories/getCategoryById")
                .param("categoryId", "cat-123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value("cat-123"));
    }

    @Test
    void getCategoryById_NotFound() throws Exception {
        when(categoryService.getCategoryById("999")).thenThrow(new ResourceNotFoundException("Category", "id", "999"));

        mockMvc.perform(get("/v1/categories/getCategoryById")
                .param("categoryId", "999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Category not found with id : '999'"));
    }

    @Test
    void updateCategory_Success() throws Exception {
        when(categoryService.updateCategory(eq("cat-123"), any(CategoryRequest.class)))
                .thenReturn(categoryResponse);

        mockMvc.perform(put("/v1/categories/update/{categoryId}", "cat-123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryName").value("Test Category"));
    }

    @Test
    void deleteCategory_Success() throws Exception {
        mockMvc.perform(delete("/v1/categories/delete/{categoryId}", "cat-123"))
                .andExpect(status().isOk());
    }
}
