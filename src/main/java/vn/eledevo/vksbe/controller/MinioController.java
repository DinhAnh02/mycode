package vn.eledevo.vksbe.controller;

import java.io.InputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import vn.eledevo.vksbe.utils.TrimData.Trimmed;
import vn.eledevo.vksbe.utils.minio.MinioService;

@RestController
@RequestMapping("/api/v1/private/minio")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Minio Store")
public class MinioController {
    MinioService minioService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        return minioService.uploadFile(file);
    }

    // Endpoint để download file từ MinIO
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) throws Exception {
        InputStream inputStream = minioService.downloadFile(fileName);

        byte[] fileBytes = inputStream.readAllBytes();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileBytes);
    }

    // Endpoint để xóa file khỏi MinIO
    @DeleteMapping("/delete")
    public String deleteFile(@RequestBody String fileName) throws Exception {
        minioService.deleteFile(fileName);
        return "File deleted successfully: " + fileName;
    }
}
