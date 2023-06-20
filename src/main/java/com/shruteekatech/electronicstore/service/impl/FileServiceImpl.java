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
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        //original name:- abc.png
        String originalFilename = file.getOriginalFilename();

        //extracting extension from original file name which is after last "." eg:- ".png" , ".jpg"
        String ext = Objects.requireNonNull(originalFilename).substring(originalFilename.lastIndexOf("."));

        // generating random string with extension eg:- hrf789-378nhs-jsj92.jpg
        String imageName = UUID.randomUUID().toString().concat(ext);

        //creating image path file with name and extension, eg:  img/hrf789-378nhs-jsj92.png
        String imageFileWithPathName = path.concat(File.separator).concat(imageName);
        if (ext.equalsIgnoreCase(".png") || ext.equalsIgnoreCase(".jpg") || ext.equalsIgnoreCase(".jpeg")) {

            File folder = new File(path);  //new file object
            if (!folder.exists()) {   //checking if folder exist
                folder.mkdirs();    //creating folder if it doesn't exist
            }
            Files.copy(file.getInputStream(), Paths.get(imageFileWithPathName));  //copying file to given path
            return imageName;
        } else {
            throw new ResourceNotFoundException(AppConstants.IMG_NA);
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullPath = path.concat(File.separator).concat(name);
        return new FileInputStream(fullPath);
    }
}