package com.ecommerce.productservice.mapper;

import com.ecommerce.productservice.entities.Category;
import com.ecommerce.productservice.requests.CategoryRequest;
import com.ecommerce.productservice.responses.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toEntity(CategoryRequest request);

    CategoryResponse toResponse(Category category);
}
