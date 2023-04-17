package com.example.technomarketproject.controller.services;

import com.example.technomarketproject.model.DTOs.SimpleProductDTO;
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
import java.util.Optional;
import java.util.UUID;

@Service
public class MediaService extends AbstractService {

    @SneakyThrows
    @Transactional
    public SimpleProductDTO upload(MultipartFile origin, int productId, int loggedId) {

        if(!userRepository.findById(loggedId).get().isAdmin()) {
            throw new UnauthorizedException("Only admins can upload product images!");
        }

        Optional<Product> opt = productRepository.findById(productId);
        if(opt.isEmpty()) {
            throw new FileNotFoundException("No such product!");
        }
        Product p = opt.get();

        String fileName = UUID.randomUUID().toString();
        String extension = FilenameUtils.getExtension(origin.getOriginalFilename());

        fileName = fileName + "." + extension;

        File dir = new File("uploads");
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File f = new File(dir, fileName);

        Files.copy(origin.getInputStream(), f.toPath());

        String path = dir.getName() + File.separator + f.getName();

        p.setProductImageUrl(path);
        productRepository.save(p);
        return mapper.map(p, SimpleProductDTO.class);

    }

    public File download(String fileName) {
        fileName = "uploads" + File.separator + fileName;
        File f = new File(fileName);
        if(f.exists()) {
            return f;
        }
        throw new FileNotFoundException("File not found!");
    }

    public void delete(String fileName, int loggedId) {

        if(!userRepository.findById(loggedId).get().isAdmin()) {
            throw new UnauthorizedException("Only admins can delete product images!");
        }

        fileName = "uploads" + File.separator + fileName;
        File f = new File(fileName);
        if(f.exists()) {
            f.delete();
        }
        else {
            throw new FileNotFoundException("File not found!");
        }
    }
}
