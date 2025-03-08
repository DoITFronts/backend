package com.codeit.side.common.adapter.out.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.codeit.side.common.adapter.exception.IllegalRequestException;
import com.codeit.side.common.application.port.out.FileUploadOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class fileUploadAdapter implements FileUploadOutputPort {
    private static final List<String> VALID_EXTENSIONS = List.of("jpg", "jpeg", "gif", "png");
    private static final Long MAX_IMAGE_MB_SIZE = 5L;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;

    public void uploadImageToS3(MultipartFile file, String path, String fileName, String extension) {
        if (file == null || file.isEmpty()) {
            return;
        }
        try {
            InputStream is = file.getInputStream();
            byte[] bytes = IOUtils.toByteArray(is);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/" + extension);
            metadata.setContentLength(bytes.length);

            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, path + "/" + fileName, byteArrayInputStream, metadata)
                        .withCannedAcl(null);

                amazonS3.putObject(putObjectRequest);
            } catch (Exception e) {
                e.printStackTrace();
                throw new AmazonS3Exception("Failed to upload image to S3");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new AmazonS3Exception("Failed to upload image to S3");
        }
        return;
    }

    @Override
    public void validateImage(MultipartFile image) {
        if (image == null) {
            return;
        }
        if (!VALID_EXTENSIONS.contains(StringUtils.getFilenameExtension(image.getOriginalFilename()))) {
            throw new IllegalRequestException(String.format("이미지는 %s 형식이어야 합니다.", String.join(", ", VALID_EXTENSIONS)));
        }
        long size = image.getSize();
        if (size > 1024 * 1024 * MAX_IMAGE_MB_SIZE) {
            throw new IllegalRequestException("이미지 크기는 %sMB 이하여야 합니다.".formatted(MAX_IMAGE_MB_SIZE));
        }
    }
}
