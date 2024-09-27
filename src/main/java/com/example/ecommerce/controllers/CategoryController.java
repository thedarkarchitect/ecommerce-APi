package com.example.ecommerce.controllers;

import com.example.ecommerce.dto.CategoryDTO;
import com.example.ecommerce.response.ApiResponse;
import com.example.ecommerce.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController // This annotation is used to indicate that the class defines a REST controller.
@RequiredArgsConstructor // This annotation generates a constructor with required fields.
@RequestMapping("${api.prefix}/categories") // This annotation is used to map web requests onto specific handler classes and/or handler methods.
public class CategoryController {

    //injection of the CategoryService class
    private final CategoryService categoryService;

    @GetMapping("/all") // This annotation is used to map HTTP GET requests onto specific handler methods.
    public ResponseEntity<ApiResponse> getAllCategories() {
        try{
            List<CategoryDTO> categories = categoryService.getAllCategories().stream() // This method returns all the categories from the database.
                    .map(categoryService::convertToDto) // This method maps the category to DTO.
                    .toList(); // This method collects the categories to a list.
            return ResponseEntity.ok(new ApiResponse("Success", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Failure", e.getMessage()));
        }
    }

    @PostMapping("/add") // This annotation is used to map HTTP POST requests onto specific handler methods.
    public ResponseEntity<ApiResponse> addCategory(@RequestBody CategoryDTO name) {
        try{
            CategoryDTO category = categoryService.convertToDto(categoryService.saveCategory(categoryService.convertToEntity(name))); // This method saves the category in the database.
            return ResponseEntity.ok(new ApiResponse("Success", category));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Failure", e.getMessage()));
        }
    }

    @GetMapping("/category/{id}/") // This annotation is used to map HTTP GET requests onto specific handler methods.
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        try{
            CategoryDTO category = categoryService.convertToDto(categoryService.getCategoryById(id)); // This method returns the category by id from the database.
            return ResponseEntity.ok(new ApiResponse("Success", category));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Failure", e.getMessage()));
        }
    }

    @GetMapping("/category/categoryName/{name}") // This annotation is used to map HTTP GET requests onto specific handler methods.
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name) {
        try{
            CategoryDTO category = categoryService.convertToDto(categoryService.getCategoryByName(name)); // This method returns the category by name from the database.
            return ResponseEntity.ok(new ApiResponse("Success", category));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Failure", e.getMessage()));
        }
    }

    @PutMapping("/category/{id}/update") // This annotation is used to map HTTP PUT requests onto specific handler methods.
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryRequest) {
        try{
            CategoryDTO category = categoryService.convertToDto(categoryService.updateCategory(id, categoryRequest)); // This method updates the category in the database.
            return ResponseEntity.ok(new ApiResponse("Success", category));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Failure", e.getMessage()));
        }
    }

    @DeleteMapping("/category/{id}/delete") // This annotation is used to map HTTP DELETE requests onto specific handler methods.
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        try{
            categoryService.deleteCategory(id); // This method deletes the category from the database. because there is no return type. DTO is not needed.
            return ResponseEntity.ok(new ApiResponse("Success", "Category deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Failure", e.getMessage()));
        }
    }
}
