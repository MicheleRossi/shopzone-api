package it.microssi.ecofish.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    String saveImage(MultipartFile file) throws IOException;
}