package it.microssi.ecofish.productimage;

import java.io.File;
import java.nio.file.Paths;

import it.microssi.ecofish.product.Product;
import it.microssi.ecofish.product.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import it.microssi.ecofish.storage.FileStorageService;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductImageController {

    private final ProductRepository productRepo;
    private final ProductImageRepository imageRepo;
    private final FileStorageService storageService;

    public ProductImageController(ProductRepository productRepo, ProductImageRepository imageRepo, FileStorageService storageService) {
        this.productRepo = productRepo;
        this.imageRepo = imageRepo;
        this.storageService = storageService;
    }

    @PostMapping("/products/{productId}/images")
    public ResponseEntity<ProductImage> uploadImage(
            @PathVariable Long productId,
            @RequestParam("file") MultipartFile file) throws IOException {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String imageUrl = storageService.saveImage(file);

        ProductImage image = new ProductImage();
        image.setUrl(imageUrl);
        image.setProduct(product);
        imageRepo.save(image);

        return ResponseEntity.ok(image);
    }

    @DeleteMapping("/product-images/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        ProductImage image = imageRepo.findById(imageId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Elimina il file dal file system
        File file = new File("uploads/products/" + Paths.get(image.getUrl()).getFileName());
        if (file.exists()) {
            file.delete();
        }

        imageRepo.delete(image);
        return ResponseEntity.noContent().build();
    }
}