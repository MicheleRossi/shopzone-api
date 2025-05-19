package it.microssi.ecofish.productimage;

import java.io.File;
import java.nio.file.Paths;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Product Images", description = "Product image management APIs")
public class ProductImageController {

    private final ProductRepository productRepo;
    private final ProductImageRepository imageRepo;
    private final FileStorageService storageService;

    public ProductImageController(ProductRepository productRepo, ProductImageRepository imageRepo, FileStorageService storageService) {
        this.productRepo = productRepo;
        this.imageRepo = imageRepo;
        this.storageService = storageService;
    }

    @Operation(
            summary = "Upload product image",
            description = "Uploads an image for a specific product"
    )
    @ApiResponse(responseCode = "200", description = "Image successfully uploaded")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @PostMapping("/products/{productId}/images")
    public ResponseEntity<ProductImage> uploadImage(
            @Parameter(description = "ID of the product") @PathVariable Long productId,
            @Parameter(description = "Image file to upload") @RequestParam("file") MultipartFile file) throws IOException {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String imageUrl = storageService.uploadFile(file);

        ProductImage image = new ProductImage();
        image.setUrl(imageUrl);
        image.setProduct(product);
        imageRepo.save(image);

        return ResponseEntity.ok(image);
    }

    @Operation(
            summary = "Delete product image",
            description = "Deletes a product image from the system"
    )
    @ApiResponse(responseCode = "204", description = "Image successfully deleted")
    @ApiResponse(responseCode = "404", description = "Image not found")
    @DeleteMapping("/product-images/{imageId}")
    public ResponseEntity<Void> deleteImage(@Parameter(description = "ID of the image to delete") @PathVariable Long imageId) {
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