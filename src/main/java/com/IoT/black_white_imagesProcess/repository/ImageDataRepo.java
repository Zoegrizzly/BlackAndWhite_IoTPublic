package com.IoT.black_white_imagesProcess.repository;

import com.IoT.black_white_imagesProcess.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageDataRepo extends JpaRepository<ImageData, Long> {
    Optional<ImageData> findByDeviceIdAndTimestamp(String deviceId, String timestamp);
}
