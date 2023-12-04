package com.bikkadit.electronic.store.services.impl;

import com.bikkadit.electronic.store.exceptions.BadApiRequestException;
import com.bikkadit.electronic.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {
    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadImage(MultipartFile file, String path) throws IOException {
        logger.info("Initiating DAO call for upload image ");
        String originalFilename = file.getOriginalFilename();
        logger.info("Filename : {}", originalFilename);
        String fileName = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = fileName + extension;
        String fullPathWithFileName= path+fileNameWithExtension;

        if(extension.equalsIgnoreCase(".png")||extension.equalsIgnoreCase(".jpg")||extension.equalsIgnoreCase(".jpeg")){

            File folder = new File(path);
            if(!folder.exists()){
                folder.mkdirs();
            }
            Files.copy(file.getInputStream(),Paths.get(fullPathWithFileName));
            logger.info("Completed DAO call for upload image ");
            return fileNameWithExtension;
        }else {
            throw new BadApiRequestException("File with "+extension+ " extension "+ "not allowed!");
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        logger.info("Initiating DAO call for serve the image");
        String fullPath = path+File.separator+name;
        InputStream inputStream = new FileInputStream(fullPath);
        logger.info("Completed DAO call for serve the image");
        return inputStream;
    }
}
