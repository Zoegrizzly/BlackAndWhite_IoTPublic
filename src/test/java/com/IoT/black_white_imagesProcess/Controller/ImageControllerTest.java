package com.IoT.black_white_imagesProcess.Controller;

import com.IoT.black_white_imagesProcess.controller.ImageController;
import com.IoT.black_white_imagesProcess.service.ImageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Map;

import javax.imageio.ImageIO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImageControllerTest {

    @Mock
    private ImageService imageService;

    @InjectMocks
    private ImageController imageController;

    public ImageControllerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUploadImage_BlackAndWhite() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("image", "test.png", "image/png", new byte[]{0, 1, 2});
        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageFile.getBytes()));

        //when(imageService.saveImages(anyString(), anyString(), anyString())).thenReturn(null);
        ResponseEntity<?> response = imageController.uploadImage("device123", "2023-01-01T12:00:00", imageFile);

        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void testUploadImage_NotBlackAndWhite() throws Exception {
        // Mock an image that is not black and white
        BufferedImage bufferedImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);
        bufferedImage.setRGB(0, 0, 0xFF0000); // Set a red pixel

        MockMultipartFile imageFile = new MockMultipartFile("image", "test.png", "image/png", new byte[]{0, 1, 2});

        ResponseEntity<?> response = imageController.uploadImage("device123", "2023-01-01T12:00:00", imageFile);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testRecoverImage_Found() {
        String deviceId = "device123";
        String timestamp = "2023-01-01T12:00:00";
        String imageData = "imageData";

        when(imageService.recoverImage(deviceId, timestamp)).thenReturn(imageData);

        ResponseEntity<?> response = imageController.recoverImage(deviceId, timestamp);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("imageData", ((Map<?, ?>) response.getBody()).get("imageData"));
    }

    @Test
    public void testRecoverImage_NotFound() {
        String deviceId = "device123";
        String timestamp = "2023-01-01T12:00:00";

        when(imageService.recoverImage(deviceId, timestamp)).thenThrow(new RuntimeException("Image not found"));

        ResponseEntity<?> response = imageController.recoverImage(deviceId, timestamp);

        assertEquals(404, response.getStatusCodeValue());
    }
}

