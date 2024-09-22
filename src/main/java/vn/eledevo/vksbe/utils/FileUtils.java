package vn.eledevo.vksbe.utils;

import java.util.Arrays;
import java.util.UUID;

public class FileUtils {

    private FileUtils() {}

    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }

    public static String getContentType(String fileName) {
        String fileExtension = getFileExtension(fileName).toLowerCase();
        return switch (fileExtension) {
            case ".png" -> "image/png";
            case ".jpg", ".jpeg" -> "image/jpeg";
            default -> "application/octet-stream";
        };
    }

    public static boolean isAllowedExtension(String fileExtension, String[] allowedExtensions) {
        return Arrays.asList(allowedExtensions).contains(fileExtension.toLowerCase());
    }

    public static boolean isPathAllowedExtension(String path, String[] allowedExtensions) {
        return Arrays.stream(allowedExtensions)
                .anyMatch(ext -> path.toLowerCase().endsWith(ext));
    }

    public static String generateUniqueFileName(String originalFileName) {
        String fileExtension = getFileExtension(originalFileName);
        return UUID.randomUUID() + fileExtension;
    }
}
