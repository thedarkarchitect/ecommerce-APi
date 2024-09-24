package com.example.ecommerce.service.image;

import com.example.ecommerce.dto.ImageDTO;
import com.example.ecommerce.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(Long id);
    void deleteImage(Long id);
    List<ImageDTO> saveImages(Long productId, List<MultipartFile> files);
    void updateImage(Long id, MultipartFile file);
}


