package it.microssi.ecofish.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalFileStorageService implements FileStorageService {

    private static final String UPLOAD_DIR = "uploads/products/";

    @Override
    public String saveImage(MultipartFile file) throws IOException {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR, filename);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        return "/uploads/products/" + filename;
    }
}