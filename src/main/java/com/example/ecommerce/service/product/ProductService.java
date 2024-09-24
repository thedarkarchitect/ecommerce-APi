package com.example.ecommerce.service.product;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.request.CreateProductRequest;
import com.example.ecommerce.request.ProductUpdateRequest;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product saveProduct(CreateProductRequest product);
    Product updateProduct(ProductUpdateRequest product, Long id);
    void deleteProduct(Long id);

    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countByBrandAndName(String brand, String name);
    List<ProductDTO> getConvertedProducts(List<Product> products);

    ProductDTO convertToDto(Product product);

}
