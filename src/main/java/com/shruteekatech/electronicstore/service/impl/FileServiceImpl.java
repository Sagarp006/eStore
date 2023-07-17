package com.shruteekatech.electronicstore.service.impl;

import com.shruteekatech.electronicstore.exceptions.ResourceNotFoundException;
import com.shruteekatech.electronicstore.helper.AppConstants;
import com.shruteekatech.electronicstore.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.function.UnaryOperator;


@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
        public String uploadFile(MultipartFile file, String path) throws IOException {
            log.info("image is being uploaded");

            String ext = getExt.apply(file.getOriginalFilename());

            if (ext.matches("\\.(png|jpg|jpeg)$")) {
                //generating imageName eg:- 7yjw93-j94093.png
                String imageName = UUID.randomUUID() + ext;

                //generating path eg:- image/category/7yjw93-j94093.png
                String imageFileWithPathName = path + imageName;

                //uploading file to a given path
                Files.copy(file.getInputStream(), Paths.get(imageFileWithPathName));
                log.info("image uploaded successfully");
                return imageName;
            } else {
                log.info("Selected File is Not an Image");
                throw new ResourceNotFoundException(AppConstants.IMG_NA);
            }
        }
    @Override
    public InputStream getResource(String path, String name) {
        String fullPath = path.concat(File.separator).concat(name);
        try {
            return new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    // @returns image extension eg:- .png
    private final UnaryOperator<String> getExt = originalFilename -> {
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new ResourceNotFoundException("File is empty");
        }
        return originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
    };
}

