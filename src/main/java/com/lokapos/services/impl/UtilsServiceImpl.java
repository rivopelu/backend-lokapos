package com.lokapos.services.impl;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.lokapos.exception.SystemErrorException;
import com.lokapos.model.response.ResponseUrl;
import com.lokapos.services.UtilsService;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {
    @Override
    public ResponseUrl uploadFile(MultipartFile multipartFile, String folder) throws BadRequestException {
        if (folder == null || folder.isEmpty()) {
            throw new BadRequestException("Folder required");
        }
        try {
            String fileName = multipartFile.getOriginalFilename();
            if (fileName != null) {
                fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.
            }
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            File file = this.convertToFile(image, fileName);
            String URL = this.uploadFile(file, fileName, folder);
            file.delete();
            return ResponseUrl.builder().url(URL).build();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }


    private String getExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    private File convertToFile(BufferedImage image, String fileName) throws IOException {
        File tempFile = new File(fileName);
        ImageIO.write(image, "png", tempFile); // Assuming PNG format for merged image
        return tempFile;
    }

    private String uploadFile(File file, String fileName, String folder) throws IOException {
        String bucketName = "storage-sentrum-stagging.appspot.com";
        String fullPath = folder + "/" + fileName; // Adding folder structure
        BlobId blobId = BlobId.of(bucketName, fullPath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = UtilsService.class.getClassLoader().getResourceAsStream("firebase.json");
        if (inputStream == null) {
            throw new BadRequestException("Firebase upload failed");
        }
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/" + bucketName + "/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fullPath, StandardCharsets.UTF_8));
    }
}
