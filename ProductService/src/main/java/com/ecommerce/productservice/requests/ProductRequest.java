package com.ecommerce.productservice.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductRequest {
    @NotBlank(message = "Product name cannot be blank")
    private String name;
    private String description;
    @Positive(message = "Price must be positive")
    private BigDecimal price;
    @NotBlank(message = "Category name cannot be blank")
    private String categoryName;
}
