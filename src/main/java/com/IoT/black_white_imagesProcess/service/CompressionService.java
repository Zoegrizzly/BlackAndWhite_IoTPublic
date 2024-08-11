package com.IoT.black_white_imagesProcess.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompressionService {

    public byte[] compressImageRLE(byte[] imageData){
        List<Byte> compressed = new ArrayList<>();
        byte prev = imageData[0];
        int count = 1;

        for (int i = 1; i < imageData.length; i++) {
            if (imageData[i] == prev) {
                count++;
            } else {
                compressed.add(prev);
                compressed.add((byte) count);
                prev = imageData[i];
                count = 1;
            }
        }
        compressed.add(prev);
        compressed.add((byte) count);

        byte[] result = new byte[compressed.size()];
        for (int i = 0; i < compressed.size(); i++) {
            result[i] = compressed.get(i);
        }

        return result;

    }

    public byte[] decompressImageRLE(byte[] compressedData){
        List<Byte> decompressed = new ArrayList<>();

        for (int i = 0; i < compressedData.length; i += 2) {
            byte value = compressedData[i];
            int count = compressedData[i + 1];

            for (int j = 0; j < count; j++) {
                decompressed.add(value);
            }
        }

        byte[] result = new byte[decompressed.size()];
        for (int i = 0; i < decompressed.size(); i++) {
            result[i] = decompressed.get(i);
        }

        return result;
    }
}
