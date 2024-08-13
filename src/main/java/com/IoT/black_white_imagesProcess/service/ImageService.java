package com.IoT.black_white_imagesProcess.service;

import com.IoT.black_white_imagesProcess.model.ImageData;
import com.IoT.black_white_imagesProcess.repository.ImageDataRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageDataRepo imageDataRepo;

    @Autowired
    private CompressionService compressionService;

    public void saveImages(String deviceId, String timestamp, String base64Image){

        byte[] imageData = Base64.getDecoder().decode(base64Image);
        byte[] compressedData = compressionService.compressImageRLE(imageData);

        ImageData newImage = new ImageData();
        newImage.setDeviceId(deviceId);
        newImage.setTimestamp(timestamp);
        newImage.setImageData(compressedData);

        imageDataRepo.save(newImage);
    }

    public String recoverImage(String deviceId, String timestamp){
        return imageDataRepo.findByDeviceIdAndTimestamp(deviceId, timestamp)
                .map(imageData -> {
                    byte[] decompressedData = compressionService.decompressImageRLE(Base64.getDecoder().decode(imageData.getImageData()));
                    return Base64.getEncoder().encodeToString(decompressedData);
                })
                .orElseThrow(() -> new RuntimeException("Image not found"));
    }

    public boolean updateImage(String deviceId, String timestamp, String base64Image) {
        Optional<ImageData> existingImageOpt = imageDataRepo.findByDeviceIdAndTimestamp(deviceId, timestamp);

        if (existingImageOpt.isPresent()) {
            ImageData existingImage = existingImageOpt.get();

            byte[] imageData = Base64.getDecoder().decode(base64Image);
            byte[] compressedData = compressionService.compressImageRLE(imageData);

            existingImage.setImageData(compressedData);
            imageDataRepo.save(existingImage);

            return true;
        } else {
            return false;
        }
    }
}
