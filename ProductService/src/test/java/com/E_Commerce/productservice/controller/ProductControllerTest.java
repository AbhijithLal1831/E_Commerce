package com.E_Commerce.productservice.controller;

import com.E_Commerce.productservice.requests.ProductRequest;
import com.E_Commerce.productservice.responses.ProductResponse;
import com.E_Commerce.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // Correct import for newer Spring Boot
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable security filters for this test
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductRequest productRequest;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        productRequest = new ProductRequest();
        productRequest.setName("Test Product");
        productRequest.setDescription("Test Description");
        productRequest.setPrice(BigDecimal.valueOf(100.0));
        productRequest.setCategoryName("Test Category");

        productResponse = new ProductResponse();
        productResponse.setProductId("123");
        productResponse.setName("Test Product");
        productResponse.setDescription("Test Description");
        productResponse.setPrice(BigDecimal.valueOf(100.0));
        productResponse.setCategoryName("Test Category");
    }

    @Test
    void createProduct_Success() throws Exception {
        when(productService.createProduct(any(ProductRequest.class))).thenReturn(productResponse);

        mockMvc.perform(post("/v1/products/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("123"))
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void createProduct_ValidationFailure() throws Exception {
        productRequest.setName(""); // Invalid: Blank
        productRequest.setPrice(BigDecimal.valueOf(-1)); // Invalid: Negative

        mockMvc.perform(post("/v1/products/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getProductById_Success() throws Exception {
        when(productService.getProductById("123")).thenReturn(Optional.of(productResponse));

        mockMvc.perform(get("/v1/products/getProductById")
                .param("productId", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("123"));
    }

    @Test
    void getProductById_NotFound() throws Exception {
        when(productService.getProductById("999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/v1/products/getProductById")
                .param("productId", "999"))
                .andExpect(status().isOk()) // Controller returns Optional.empty which is null body or 200 with null
                .andExpect(content().string("null"));
    }

    @Test
    void updateProduct_Success() throws Exception {
        when(productService.updateProduct(eq("123"), any(ProductRequest.class)))
                .thenReturn(Optional.of(productResponse));

        mockMvc.perform(put("/v1/products/update/{productId}", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void deleteProduct_Success() throws Exception {
        mockMvc.perform(delete("/v1/products/delete/{productId}", "123"))
                .andExpect(status().isOk());
    }
}
