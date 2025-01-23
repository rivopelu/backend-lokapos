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
    public ResponseUrl uploadFile(MultipartFile multipartFile) {
        try {
            String fileName = multipartFile.getOriginalFilename();
            if (fileName != null) {
                fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.
            }
            BufferedImage image = ImageIO.read(multipartFile.getInputStream());
            File file = this.convertToFile(image, fileName);
            String URL = this.uploadFile(file, fileName);
            file.delete();
            return ResponseUrl.builder().url(URL).build();
        } catch (Exception e) {
            throw new SystemErrorException(e);
        }
    }

    private File resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        // Create a new image with the desired dimensions
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());

        // Get the Graphics2D object for drawing
        Graphics2D graphics2D = resizedImage.createGraphics();

        // Set rendering hints for better quality (optional but recommended)
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Draw the original image onto the resized image
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();

        // Get a unique filename for the resized image
        String fileName = "resized_" + System.currentTimeMillis() + ".jpg"; // Or your preferred extension
        File file = new File(fileName);

        // Save the resized image
        ImageIO.write(resizedImage, "jpg", file);

        return file;
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

    private String uploadFile(File file, String fileName) throws IOException {
        String bucketName = "storage-sentrum-stagging.appspot.com";
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = UtilsService.class.getClassLoader().getResourceAsStream("firebase.json");
        if (inputStream == null) {
            throw new BadRequestException("Firebase upload failed");
        }
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/" + bucketName + "/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }
}
