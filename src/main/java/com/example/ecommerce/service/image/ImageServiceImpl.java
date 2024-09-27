package com.example.ecommerce.service.image;

import com.example.ecommerce.dto.ImageDTO;
import com.example.ecommerce.models.Image;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.repository.ImageRepository;
import com.example.ecommerce.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with id " + id));
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse( imageRepository::delete, () -> {
                    throw new RuntimeException("Image not found with id " + id);
                });
    }

    @Override
    public List<ImageDTO> saveImages(Long productId, List<MultipartFile> files) {
        Product product = productService.getProductById(productId);

        List<ImageDTO> saveImageDto = new ArrayList<>();
        for (MultipartFile file: files){
            try{
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl + image.getId(); // image id is temporary because it is not saved in the database yet so id = null
                image.setDownloadUrl(downloadUrl); // This is a temporary download URL and id is null
                Image savedImage = imageRepository.save(image); // Save image to the database and id is generated

                savedImage.setDownloadUrl(buildDownloadUrl + savedImage.getId()); // we redo this step to get the id from the database of the saved image
                imageRepository.save(savedImage); // Save image to the database

                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setId(savedImage.getId());
                imageDTO.setFileName(savedImage.getFileName());
                imageDTO.setDownloadUrl(savedImage.getDownloadUrl());

                saveImageDto.add(imageDTO);

            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        return saveImageDto;
    }

    @Override
    public void updateImage(Long id, MultipartFile file) {
        Image image = getImageById(id);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
