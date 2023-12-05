package com.bikkadit.electronic.store.services;

import com.bikkadit.electronic.store.dtos.CategoryDto;
import com.bikkadit.electronic.store.helper.PageableResponse;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto,String categoryId);

    CategoryDto getCategoryById(String categoryId);

    PageableResponse<CategoryDto> getAllCategories(Integer pageNum, Integer pageSize, String sortBy, String sortDir);

    void deleteCategory(String categoryId);
}
