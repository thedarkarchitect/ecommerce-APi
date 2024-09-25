package com.example.ecommerce.repository;

import com.example.ecommerce.models.Category;
import com.example.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // This annotation is used to indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
public interface ProductRepository extends JpaRepository<Product, Long> { // JpaRepository is a JPA specific extension of Repository. It contains the full API of CrudRepository and PagingAndSortingRepository.
    List<Product> findByCategoryName(String categoryName); // This method is used to find products by category name.
    List<Product> findByBrand(String brand); // This method is used to find products by brand name.
    List<Product> findByCategory_NameAndBrand(String categoryName, String brand); // This method is used to find products by category and brand name.
    List<Product> findByName(String name); // This method is used to find products by name.
    List<Product> findByBrandAndName(String brandName, String name); // This method is used to find products by brand and name.
    Long countByBrandAndName(String brand, String name); // This method is used to count the number of products by brand and name.
}
