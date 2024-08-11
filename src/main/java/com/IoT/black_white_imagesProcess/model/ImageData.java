package com.IoT.black_white_imagesProcess.model;

import jakarta.persistence.*;

@Entity
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String deviceId;
    private String timestamp;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] imageData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    @Override
    public String toString() {
        return "ImageData{" +
                "id=" + id +
                ", deviceId='" + deviceId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", imageData='" + imageData + '\'' +
                '}';
    }
}


