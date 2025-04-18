package com.codeit.side.common.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadOutputPort {
    void uploadImageToS3(MultipartFile file, String path, String fileName, String extension);

    void validateImage(MultipartFile image);
}