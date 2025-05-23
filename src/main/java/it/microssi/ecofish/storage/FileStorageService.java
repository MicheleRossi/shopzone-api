package it.microssi.ecofish.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    String uploadFile(MultipartFile file) throws IOException;
    byte[] downloadFile(String fileName) throws IOException;
    void deleteFile(String fileName) throws IOException;
}