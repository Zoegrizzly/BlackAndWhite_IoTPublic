package com.IoT.black_white_imagesProcess.controller;

import com.IoT.black_white_imagesProcess.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@RestController
@Tag(name = "ModuleProcessBlackAndWhiteImagesDevices", description = "Modules with APIs that process the Black and White Images from Devices")
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload,save and compress images")
    public ResponseEntity<?> uploadImage(
            @RequestParam("deviceId") String deviceId,
            @RequestParam("timestamp") String timestamp,
            @RequestPart("image") MultipartFile imageFile) {

        return processImage(deviceId, timestamp, imageFile, false);
    }

    @PutMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Re-upload, update and compress image")
    public ResponseEntity<?> updateImage(
            @RequestParam("deviceId") String deviceId,
            @RequestParam("timestamp") String timestamp,
            @RequestPart("image") MultipartFile imageFile) {
        return processImage(deviceId, timestamp, imageFile, true);
    }


    @GetMapping("/recover")
    @Operation(summary = "get an images knowing id and timestamp")
    public ResponseEntity<?> recoverImage(@RequestParam String deviceId, @RequestParam String timestamp){
        try {
            String imageData = imageService.recoverImage(deviceId, timestamp);
            return ResponseEntity.ok(Map.of("imageData", imageData));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("SoRRY No Image founded!");
        }
    }



    //Short function to detect if a image is black and white
    private boolean isBlackAndWhite(BufferedImage image) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;
                if (red != green || green != blue) {
                    return false; //Is a colorfully image
                }
            }
        }
        return true;
    }

    //Save and Actualize images Process
    private ResponseEntity<?> processImage(
            String deviceId,
            String timestamp,
            MultipartFile imageFile,
            boolean isUpdate) {
        try {
            //First check if the image is black and white
            BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());
            if (!isBlackAndWhite(bufferedImage)) {
                return ResponseEntity.status(400).body("Error: The image is not black and white.");
            }

            // Get the ImageData as byte[]
            byte[] imageData = imageFile.getBytes();

            /*
            Not necessarily needed for the objective of this exercise. We could use for the complete flow a byte[].
            But we will be using a JSON format to show the results and base64 is compatible with a lot of formats
             */
            String base64Image = Base64.getEncoder().encodeToString(imageData);

            // Save or update the image according to the falg
            if (isUpdate) {
                boolean isUpdated = imageService.updateImage(deviceId, timestamp, base64Image);
                if (isUpdated) {
                    return ResponseEntity.status(200).body("Image updated successfully!");
                } else {
                    return ResponseEntity.status(404).body("Error: Image not found for the given deviceId and timestamp.");
                }
            } else {
                imageService.saveImages(deviceId, timestamp, base64Image);
                return ResponseEntity.status(201).body("Upload successfully!");
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error processing the image");
        }
    }

}
