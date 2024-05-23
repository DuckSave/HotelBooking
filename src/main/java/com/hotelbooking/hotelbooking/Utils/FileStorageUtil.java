package com.hotelbooking.hotelbooking.Utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStorageUtil {

    public static String saveFile(MultipartFile file, String folder) {
        try {
            if (file.isEmpty()) {
                return null;
            }

            // Create the directory if it doesn't exist
            Path directoryPath = Paths.get(folder);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // Generate a unique filename
            String originalFileName = file.getOriginalFilename();
            String fileName = System.currentTimeMillis() + "_" + originalFileName;

            Path filePath = directoryPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath);
            System.out.println("File saved as: " + fileName);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
