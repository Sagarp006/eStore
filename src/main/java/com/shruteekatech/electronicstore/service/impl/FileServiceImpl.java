package com.shruteekatech.electronicstore.service.impl;

import com.shruteekatech.electronicstore.exceptions.BadRequestApiException;
import com.shruteekatech.electronicstore.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {

        log.info(" Request Starting for  upload image  ");
        //get original filename
        String originalFilename = file.getOriginalFilename();
        log.info("FileName :{}", originalFilename);

        //random Image name generate
        String randomFileName = UUID.randomUUID().toString();

        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = randomFileName.concat(extension);

        //full path
        String fullPathWithFileName = path.concat(fileNameWithExtension) ;

        log.info(" full image path :{}", fullPathWithFileName);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

            log.info(" file extension :{}", extension);
            //file Save

            File f = new File(path);

            if (!f.exists()) {
                //folder creation up to multiple level
                f.mkdirs();
            }
            //upload Image
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));

            return fileNameWithExtension;
        } else {
            throw new BadRequestApiException(" File with this " + extension + " not allowed ...");
        }


    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        log.info(" Request Starting to serve image ");
        String fullPath = path + File.separator + name;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
