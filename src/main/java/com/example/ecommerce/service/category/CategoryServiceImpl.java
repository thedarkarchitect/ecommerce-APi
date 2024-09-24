package com.example.ecommerce.service.category;

import com.example.ecommerce.dto.CategoryDTO;
import com.example.ecommerce.models.Category;
import com.example.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service // This annotation is used to indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
@RequiredArgsConstructor // This annotation generates a constructor with required fields.
public class CategoryServiceImpl implements CategoryService {

    //These final variables are initialized through the constructor for dependency injection instead of using @Autowired annotation.
    //referred to as constructor-based dependency injection.
    private final ModelMapper modelMapper;// This class provides the mapping of the objects.
    private final CategoryRepository categoryRepository;// This class provides the mechanism for storage, retrieval, search, update and delete operation on objects.

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll(); // This method returns all the categories from the database.
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)// This method returns the category by id from the database.
                .orElseThrow(() -> new RuntimeException("Category not found")); // This method throws an exception if the category is not found.
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);// This method returns the category by name from the database
    }

    @Override
    public Category saveCategory(Category category) {
        return Optional.of(category)
                .filter(c -> !categoryRepository.existsByName(c.getName()))// This method checks if the category exists by name in the database.
                .map(categoryRepository::save)// This method saves the category in the database.
                .orElseThrow(() -> new RuntimeException("Category already exists")); // This method throws an exception if the category already exists.
    }

    @Override
    public Category updateCategory(Category category) {
        return Optional.ofNullable(getCategoryById(category.getId()))// This method returns the category by id from the database if it exists.
                .map(existingCategory -> { // This method maps the existing category.
                    existingCategory.setName(category.getName());// This method sets the name of the category.
                    return categoryRepository.save(existingCategory);// This method saves the category in the database after update.
                }).orElseThrow(() -> new RuntimeException("Category not found")); // This method throws an exception if the category is not found.
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)// This method returns the category by id from the database.
                .ifPresentOrElse(categoryRepository::delete, () -> { // This method deletes the category from the database if it exists.
                    throw new RuntimeException("Category not found");
                });
    }

    @Override
    public CategoryDTO convertToDto(Category category) {
        return modelMapper.map(category, CategoryDTO.class); // This method maps the category to the categoryDTO.
    }

    @Override
    public Category convertToEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class); // This method maps the categoryDTO to the category.
    }
}
