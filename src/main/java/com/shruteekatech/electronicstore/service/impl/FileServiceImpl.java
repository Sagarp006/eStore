package com.shruteekatech.electronicstore.service.impl;

import com.shruteekatech.electronicstore.exceptions.InvalidFileException;
import com.shruteekatech.electronicstore.exceptions.ResourceNotFoundException;
import com.shruteekatech.electronicstore.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;


@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadFile(MultipartFile imageFile, String path) throws IOException {
        String ext = getExt(imageFile);

        // Check if the file extension matches .png, .jpg, or .jpeg
        if (ext.matches("\\.(png|jpg|jpeg)$")) {
            // Generate a unique file name with the extension (e.g., 83c3n3-m84mv.png)
            String fileNameWithExtension = UUID.randomUUID() + ext;

            // Combine the specified image path with the generated file name and extension
            String fullPathWithFileName = path + fileNameWithExtension;
            log.info("Request for upload user image to the image path: {}", fullPathWithFileName);

            // Create the directory if it doesn't exist
            Files.createDirectories(Paths.get(path));

            // Copy the file's content to the specified full path
            Files.copy(imageFile.getInputStream(), Paths.get(fullPathWithFileName));

            log.info("Request Completed for upload user image");

            // Return the generated file name with extension
            return fileNameWithExtension;
        } else {
            log.error("Invalid file format: {}", ext);
            throw new InvalidFileException("Invalid file format. Only .png, .jpg, and .jpeg files are allowed.");
        }
    }


    @Override
    public InputStream getResource(String path, String name) {
        String fullPath = path + File.separator + name;
        try {
            return new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    private static String getExt(MultipartFile file) {
        if (file.isEmpty()) {
            log.error("File is empty");
            throw new InvalidFileException("File is empty");
        }

        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null || originalFilename.isEmpty()) {
            log.error("File name is empty");
            throw new InvalidFileException("File name is empty");
        }

        if (!originalFilename.contains(".")){
            log.error("File does not contain any extension");
            throw new InvalidFileException("File does not contain any extension");
        }

        return originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
    }

}
