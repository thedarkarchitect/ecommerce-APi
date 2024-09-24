package com.example.ecommerce.service.category;

import com.example.ecommerce.dto.CategoryDTO;
import com.example.ecommerce.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    Category saveCategory(Category category);
    Category updateCategory(Category category);
    void deleteCategory(Long id);

    CategoryDTO convertToDto(Category category);

    Category convertToEntity(CategoryDTO categoryDTO);
}
