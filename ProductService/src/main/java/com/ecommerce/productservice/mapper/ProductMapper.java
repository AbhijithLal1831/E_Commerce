package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.entities.Product;
import com.ecommerce.productservice.requests.ProductRequest;
import com.ecommerce.productservice.responses.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductRequest request);

    @Mapping(source = "category.categoryName", target = "categoryName")
    ProductResponse toResponse(Product product);
}
