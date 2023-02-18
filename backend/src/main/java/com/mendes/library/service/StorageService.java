package com.mendes.library.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {
    @Value("${storage.base-path}")
    private String basePath;

    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);

    public String storeFile(MultipartFile multipartFile) throws IOException, URISyntaxException {
        logger.info("storing file");
        this.createFolder(this.basePath);

        String hash = new DigestUtils("SHA3-256").digestAsHex(multipartFile.getInputStream());
        String pathToFile = this.getFilePath(hash);
        logger.info("writing file %s", hash);

        if (Files.copy(multipartFile.getInputStream(), Path.of(pathToFile), StandardCopyOption.REPLACE_EXISTING) > 0) {
            logger.info("file saved");
            return pathToFile;
        } else {
            logger.error("error when saving file");
            throw new IOException("Could not save the image");
        }
    }

    public InputStream readFile(String filePath) throws  IOException {
        try {
            logger.info("reading file %s", filePath);
            return new FileInputStream(filePath);
        }  catch (IOException e) {
            logger.error("could not create file %s", filePath);
            throw e;
        }
    }

    public void deleteFile(String filePath) {
        try {
            logger.info("deleting file %s", filePath);
            Files.delete(Path.of(filePath));
        } catch (IOException e) {
            logger.error("an error occured when deleting file %s: %s", filePath, e.getMessage());
        }
    }

    private String getFilePath(String filename) {
        return String.format("%s/%s", this.basePath, filename);
    }

    private void createFolder(String path)  {
        try {
            logger.info("creating directory %s", path);
            new File(path).mkdirs();
        } catch (Exception e) {
            logger.info("an error occured when creating directory %s: %s", path, e.getMessage());
            throw e;
        }
    }

}
