package com.ecommerce.productservice.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequest {
    @NotBlank(message = "Category name cannot be blank")
    private String categoryName;
}
