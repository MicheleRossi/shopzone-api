package it.microssi.ecofish.productimage;

import it.microssi.ecofish.base.BaseService;
import it.microssi.ecofish.product.Product;
import it.microssi.ecofish.product.ProductService;
import it.microssi.ecofish.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ImageService extends BaseService<ImageDTO, Image, Long> {

    private final ProductImageRepository imageRepository;
    private final FileStorageService storageService;
    private final ImageMapper imageMapper;

    public byte[] download(Long id) throws IOException {
        ImageDTO image = super.get(id);
        return storageService.downloadFile(image.getFileName());
    }

    public Image findById(Long id) {
        return imageRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ImageDTO save(Product product, MultipartFile file) throws IOException {

        // salva l'immagine sul file system
        String fileName = storageService.uploadFile(file);

        // Crea un nuovo record per l'immagine
        Image image = new Image();
        image.setFileName(fileName);
        image.setProduct(product);

        image = imageRepository.save(image);

        return imageMapper.mapToDto(image);
    }

    public void delete(Long imageId) {
        ImageDTO image = super.get(imageId);

        // Elimina il file dal file system
        try {
            storageService.deleteFile(Paths.get(image.getFileName()).getFileName().toString());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete file", e);
        }

        // Elimina la riga dalla tabella
        super.delete(imageId);

        log.info("Deleted image file: {}", image.getFileName());
    }
    public void setMainImage(Long id) {
        Image image = findById(id);
        Product product = image.getProduct();
        product.getImages().forEach(img -> img.setMainImage(false));
        image.setMainImage(true);
    }
}
