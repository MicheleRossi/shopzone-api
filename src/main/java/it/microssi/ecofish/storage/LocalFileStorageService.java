package it.microssi.ecofish.storage;

import it.microssi.ecofish.exception.FileStorageException;
import it.microssi.ecofish.properties.StorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Primary
public class LocalFileStorageService implements FileStorageService {
    private final StorageProperties storageProperties;

    @Value("${storage.upload-dir}")
    private String uploadDir;

    @Value("${storage.allowed-file-types}")
    private List<String> allowedFileTypes;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        // Validazione file
        if (file == null || file.isEmpty()) {
            throw new FileStorageException("File is empty or null");
        }
        if (!allowedFileTypes.contains(file.getContentType())) {
            throw new FileStorageException("Invalid file type. Allowed types: " + allowedFileTypes);
        }
        if (file.getSize() > storageProperties.getMaxFileSize().toBytes()) {
            throw new FileStorageException("File size exceeds limit");
        }

        // Generazione nome file
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        // Creazione directory se non esiste
        Files.createDirectories(filePath.getParent());

        // Salvataggio file
        Files.write(filePath, file.getBytes());

        return fileName;
    }

    @Override
    public byte[] downloadFile(String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir, fileName);
        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + fileName);
        }
        return Files.readAllBytes(filePath);
    }
}