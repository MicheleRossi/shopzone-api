package it.microssi.ecofish.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CloudinaryFileStorageService implements FileStorageService {
    //private final Cloudinary cloudinary;

//    public CloudinaryFileStorageService() {
//        cloudinary = new Cloudinary(ObjectUtils.asMap(
//            "cloud_name", "your-cloud-name",
//            "api_key", "your-api-key",
//            "api_secret", "your-api-secret"
//        ));
//    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
//        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), new HashMap<>());
//        return (String) uploadResult.get("url");
        return null;
    }

    @Override
    public byte[] downloadFile(String fileName) throws IOException {
        // Non necessario per Cloudinary, dato che restituisce URL pubblici
        throw new UnsupportedOperationException("Download not supported for Cloudinary");
    }

    @Override
    public void deleteFile(String fileName) throws IOException {
//        try {
//            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
//
//            if (!"ok".equals(result.get("result"))) {
//                throw new IOException("Impossibile eliminare il file da Cloudinary: " + publicId);
//            }
//        } catch (Exception e) {
//            throw new IOException("Errore durante l'eliminazione del file: " + e.getMessage());
//        }
    }
}