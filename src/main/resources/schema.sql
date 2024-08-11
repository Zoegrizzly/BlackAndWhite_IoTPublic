CREATE TABLE image_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    device_id VARCHAR(50) NOT NULL,
    timestamp VARCHAR(50) NOT NULL,
    image_data TEXT NOT NULL
);
