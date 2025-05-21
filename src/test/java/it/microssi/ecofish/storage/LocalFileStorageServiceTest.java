package it.microssi.ecofish.storage;

import it.microssi.ecofish.exception.FileStorageException;
import it.microssi.ecofish.properties.StorageProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LocalFileStorageServiceTest {
    @Mock
    private Path path;
    @Mock
    private Files files;
    @Autowired
    private LocalFileStorageService storageService;
    @Autowired
    private StorageProperties storageProperties;

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
        assertTrue(Files.exists(Path.of("uploads", fileName)));
    }

    @Test
    public void testUploadFileInvalidType() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.pdf", "application/pdf", "test content".getBytes()
        );
        assertThrows(FileStorageException.class, () -> storageService.uploadFile(file));
    }

    @Test
    public void testUploadFileTooLarge() {
        // max size + 1
        byte[] largeContent = new byte[Math.toIntExact(storageProperties.getMaxFileSize().toBytes()) + 1];
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", largeContent
        );
        assertThrows(FileStorageException.class, () -> storageService.uploadFile(file));
    }

    @AfterEach
    public void cleanup() throws IOException {
        Files.walk(Paths.get("uploads"))
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        // Log errore
                    }
                });
    }
}