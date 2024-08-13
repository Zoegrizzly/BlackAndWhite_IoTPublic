package com.IoT.black_white_imagesProcess.Services;

import com.IoT.black_white_imagesProcess.service.CompressionService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompressionServiceTest {

    private final CompressionService compressionService = new CompressionService();

    @Test
    public void testCompressImageRLE() {
        byte[] imageData = {0, 0, 0, 1, 1, 2, 2, 2};
        byte[] expectedCompressedData = {0, 3, 1, 2, 2, 3};

        byte[] compressedData = compressionService.compressImageRLE(imageData);

        assertArrayEquals(expectedCompressedData, compressedData);
    }

    @Test
    public void testDecompressImageRLE() {
        byte[] compressedData = {0, 3, 1, 2, 2, 3};
        byte[] expectedDecompressedData = {0, 0, 0, 1, 1, 2, 2, 2};

        byte[] decompressedData = compressionService.decompressImageRLE(compressedData);

        assertArrayEquals(expectedDecompressedData, decompressedData);
    }
}

