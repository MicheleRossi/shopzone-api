package it.microssi.ecofish.storage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LocalFileStorageServiceTest {
    @Autowired
    private LocalFileStorageService storageService;

    @Test
    public void testUploadFile() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
            "file", 
            "test.jpg", 
            "image/jpeg", 
            "test content".getBytes()
        );
        String fileName = storageService.uploadFile(file);
        assertNotNull(fileName);
        Path path = Path.of("uploads", fileName);
        assertTrue(Files.exists(path));

        // elimina il file di test
        Files.deleteIfExists(path);
    }
}