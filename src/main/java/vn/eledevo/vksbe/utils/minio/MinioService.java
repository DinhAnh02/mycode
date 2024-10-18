package vn.eledevo.vksbe.utils.minio;

import java.io.InputStream;
import java.net.URI;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.minio.*;
import io.minio.http.Method;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MinioService {
    MinioClient minioClient;
    MinioProperties minioProperties;

    public MinioService(MinioClient minioClient, MinioProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;

        // Tạo bucket tự động nếu chưa tồn tại
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .build())) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(minioProperties.getBucketName())
                        .build());
                System.out.println("Bucket " + minioProperties.getBucketName() + " created successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateFileUrl(String fileName) throws Exception {
        //         Xây dựng URL từ thông tin cấu hình Minio
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .bucket(minioProperties.getBucketName()) // Tên bucket
                .object(fileName) // Tên file
                .method(Method.GET) // Loại phương thức HTTP
                .build());
    }

    public String uploadFile(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        fileName = UUID.randomUUID() + "_" + fileName;
        minioClient.putObject(PutObjectArgs.builder().bucket(minioProperties.getBucketName()).object(fileName).stream(
                        file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build());

        return generateFileUrl(fileName);
    }

    public InputStream downloadFile(String fileName) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(fileName)
                .build());
    }
    // Xóa file khỏi MinIO
    public void deleteFile(String urlImage) throws Exception {
        URI uri = new URI(urlImage);
        String path = uri.getPath(); // Lấy path từ URL
        String fileNameImage = path.substring(path.lastIndexOf('/') + 1); // Trích xuất tên file từ path
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .object(fileNameImage)
                .build());
    }
}
