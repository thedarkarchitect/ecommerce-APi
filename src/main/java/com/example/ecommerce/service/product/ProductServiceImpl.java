package com.example.ecommerce.service.product;

import com.example.ecommerce.dto.ImageDTO;
import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.models.Category;
import com.example.ecommerce.models.Image;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ImageRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.request.CreateProductRequest;
import com.example.ecommerce.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // This annotation is used to indicate that the class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
@RequiredArgsConstructor // This annotation generates a constructor with required fields.
public class ProductServiceImpl implements ProductService {

    //dependency Injection
    private final ModelMapper modelMapper; // This class provides the mapping of the objects.
    private final ProductRepository productRepository; // This class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
    private final CategoryRepository categoryRepository; // This class provides the mechanism for storage, retrieval, search, update and delete operation on objects.
    private final ImageRepository imageRepository; // This class provides the mechanism for storage, retrieval, search, update and delete operation on objects.

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    @Override
    public Product saveProduct(CreateProductRequest request) {
        //check if the category exists
        //if YES, set it as the new Product category
        //if NO, then save as new Category
        //then set the new Category as the new Product category

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    public Product createProduct(
            CreateProductRequest request,
            Category category) {
        return new Product( // This method creates a new product. using constructor in the Product class. to make sure that the product is created with the required fields.
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getDescription(),
                request.getInventory(),
                category
        );
    }

    @Override
    public Product updateProduct(ProductUpdateRequest product, Long id) {
        return productRepository.findById(product.getId())
                .map(existingProduct -> updateExistingProduct(product, existingProduct))
                .map(productRepository::save)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + product.getId()));
    }

    private Product updateExistingProduct(ProductUpdateRequest product, Product existingProduct) {
        existingProduct.setName(product.getName());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setInventory(product.getInventory());

        Category category = categoryRepository.findByName(product.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete, () -> {
                    throw new RuntimeException("Product not found with id " + id);
                });
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDTO> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDTO convertToDto(Product product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDTO> imageDTOS = images.stream()
                .map(image -> modelMapper.map(image, ImageDTO.class))
                .toList();
        productDTO.setImageUrls(imageDTOS);
        return productDTO;
    }
}
