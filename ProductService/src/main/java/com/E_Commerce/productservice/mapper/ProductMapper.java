package com.E_Commerce.productservice.mapper;

import com.E_Commerce.productservice.entities.Product;
import com.E_Commerce.productservice.requests.ProductRequest;
import com.E_Commerce.productservice.responses.ProductResponse;
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
