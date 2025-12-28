package com.ecommerce.productservice.responses;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponse {
    private String productId;
    private String name;
    private String description;
    private BigDecimal price;
    private String categoryName;
}
