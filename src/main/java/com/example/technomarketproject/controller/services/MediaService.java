package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.ProductForImagesDTO;
import com.example.technomarketproject.model.entities.ProductImage;
import com.example.technomarketproject.model.entities.ProductImageKey;
import com.example.technomarketproject.model.entities.Product;
import com.example.technomarketproject.model.exceptions.FileNotFoundException;
import com.example.technomarketproject.model.exceptions.UnauthorizedException;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MediaService extends AbstractService {

    @SneakyThrows
    @Transactional
    public ProductForImagesDTO uploadProductImages(List<MultipartFile> origin, int productId, int loggedId) {

        if(!userRepository.findById(loggedId).get().isAdmin()) {
            logger.error("A non-admin user with ID " + loggedId + " tried to upload media for product with ID " + productId);
            throw new UnauthorizedException("Only admins can upload product images!");
        }

        Optional<Product> opt = productRepository.findById(productId);
        if(opt.isEmpty()) {
            logger.error("A user with ID " + loggedId + " tried to upload media for a non-existing product with ID " + productId);
            throw new FileNotFoundException("No such product!");
        }
        Product p = opt.get();

        File dir = new File("uploads");
        if(!dir.exists()) {
            dir.mkdirs();
        }

        List<String> paths = new ArrayList<>();

        for (MultipartFile file : origin) {
            String fileName = UUID.randomUUID().toString();
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());

            fileName = fileName + "." + extension;

            File f = new File(dir, fileName);
            Files.copy(file.getInputStream(), f.toPath());

            String path = dir.getName() + File.separator + f.getName();
            paths.add(path);
        }

        List<ProductImage> productImages = new ArrayList<>();

        for (int i = 0; i < paths.size(); i++) {
            ProductImageKey key = new ProductImageKey();
            key.setUrl(paths.get(i));
            key.setProductId(p.getId());
            ProductImage image = new ProductImage();
            image.setImage(paths.get(i));
            image.setProduct(p);
            image.setId(key);
            productImages.add(image);
        }
        productImageRepository.saveAll(productImages);

        logger.info("New media has been uploaded for product with ID " + p.getId() + " by user with ID " + loggedId);

        ProductForImagesDTO dto = new ProductForImagesDTO();
        dto.setId(productId);
        dto.setName(p.getName());
        dto.setImages(paths);
        return dto;
    }
    public File downloadProductImage(String fileName) {
        fileName = "uploads" + File.separator + fileName;
        File f = new File(fileName);
        if(f.exists()) {
            logger.info("A media file with name " + fileName + " has been downloaded.");
            return f;
        }
        throw new FileNotFoundException("File not found!");
    }
    @Transactional
    public void deleteProductImage(String fileName, int loggedId) {

        if(!userRepository.findById(loggedId).get().isAdmin()) {
            logger.error("A non-admin user with ID " + loggedId + " tried to remove a file with name " + fileName);
            throw new UnauthorizedException("Only admins can delete product images!");
        }

        fileName = "uploads" + File.separator + fileName;
        File f = new File(fileName);
        if(f.exists()) {
            f.delete();
            logger.info("A file with name " + fileName + " has been deleted by a user with ID " + loggedId);
            productImageRepository.deleteByImage(fileName);
        }
        else {
            throw new FileNotFoundException("File not found!");
        }
    }
    @Transactional
    public void deleteAllProductImages(int productId, int loggedId) {
        if(!userRepository.findById(loggedId).get().isAdmin()) {
            logger.error("A non-admin user with ID " + loggedId +
                    " tried to remove all media for product with ID " + productId);
            throw new UnauthorizedException("Only admins can delete product images!");
        }
        if(!productRepository.existsById(productId)) {
            logger.error("A user with ID " + loggedId +
                    " tried to remove all media for a non-existing product with ID " + productId);
            throw new FileNotFoundException("No such product!");
        }
        Product p = productRepository.findById(productId).get();
        if(productImageRepository.findAllByProduct(p).isEmpty()) {
            logger.error("A user with ID " + loggedId + " tried to remove media for a product with ID "
                    + productId + " with no media associated.");
            throw new FileNotFoundException("No images for this product!");
        }
        productImageRepository.deleteAllByProduct(p);
        logger.info("All media has been deleted for a product with ID "
                + productId + " by a user with ID " + loggedId);
    }
}
