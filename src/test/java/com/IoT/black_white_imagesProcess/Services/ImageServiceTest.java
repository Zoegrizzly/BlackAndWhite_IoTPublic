package com.IoT.black_white_imagesProcess.Services;

import com.IoT.black_white_imagesProcess.model.ImageData;
import com.IoT.black_white_imagesProcess.repository.ImageDataRepo;
import com.IoT.black_white_imagesProcess.service.CompressionService;
import com.IoT.black_white_imagesProcess.service.ImageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Base64;
import java.util.Optional;

public class ImageServiceTest {

    @Mock
    private ImageDataRepo imageDataRepo;

    @Mock
    private CompressionService compressionService;

    @InjectMocks
    private ImageService imageService;

    public ImageServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveImages() {
        String deviceId = "device123";
        String timestamp = "2023-01-01T12:00:00";
        String base64Image = Base64.getEncoder().encodeToString("imageData".getBytes());

        byte[] imageData = Base64.getDecoder().decode(base64Image);
        byte[] compressedData = "compressedData".getBytes();

        when(compressionService.compressImageRLE(imageData)).thenReturn(compressedData);

        ImageData newImage = new ImageData();
        newImage.setDeviceId(deviceId);
        newImage.setTimestamp(timestamp);
        newImage.setImageData(compressedData);

        imageService.saveImages(deviceId, timestamp, base64Image);

        verify(imageDataRepo, times(1)).save(any(ImageData.class));
    }

    @Test
    public void testRecoverImage() {
        String deviceId = "device123";
        String timestamp = "2023-01-01T12:00:00";
        byte[] decompressedData = "decompressedData".getBytes();
        byte[] compressedData = "compressedData".getBytes();

        ImageData imageData = new ImageData();
        imageData.setDeviceId(deviceId);
        imageData.setTimestamp(timestamp);
        imageData.setImageData(compressedData);

        when(imageDataRepo.findByDeviceIdAndTimestamp(deviceId, timestamp)).thenReturn(Optional.of(imageData));
        when(compressionService.decompressImageRLE(compressedData)).thenReturn(decompressedData);

        String result = imageService.recoverImage(deviceId, timestamp);

        assertEquals(Base64.getEncoder().encodeToString(decompressedData), result);
    }

    @Test
    public void testRecoverImage_NotFound() {
        String deviceId = "device123";
        String timestamp = "2023-01-01T12:00:00";

        when(imageDataRepo.findByDeviceIdAndTimestamp(deviceId, timestamp)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            imageService.recoverImage(deviceId, timestamp);
        });
    }
}

