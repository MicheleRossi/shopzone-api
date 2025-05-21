package it.microssi.ecofish.productimage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.microssi.ecofish.product.Product;
import it.microssi.ecofish.product.ProductService;
import it.microssi.ecofish.properties.StorageProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
@Tag(name = "Image", description = "Image management APIs")
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private static final String IMAGE_CONTENT_TYPE = "image/jpeg";

    private final ImageService imageService;
    private final ProductService productService;
    private final StorageProperties storageProperties;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        try {
            byte[] imageData = imageService.download(id);
            String fileName = imageService.get(id).getFileName();
            String contentType = Files.probeContentType(Paths.get(storageProperties.getUploadDir(), fileName));
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType != null ? contentType : IMAGE_CONTENT_TYPE));
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (IOException e){
            log.error("Failed to download image with id: {}", id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Upload product image",
            description = "Uploads an image for a specific product"
    )
    @ApiResponse(responseCode = "200", description = "Image successfully uploaded")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @PostMapping("/products/{productId}/images")
    public ResponseEntity<ImageDTO> uploadImage(
            @Parameter(description = "ID of the product") @PathVariable Long productId,
            @Parameter(description = "Image file to upload") @RequestParam("file") MultipartFile file) throws IOException {
        Product product = productService.findById(productId);
        ImageDTO imageDTO = imageService.save(product, file);
        return ResponseEntity.ok(imageDTO);
    }

    @Operation(
            summary = "Delete product image",
            description = "Deletes a product image from the system"
    )
    @ApiResponse(responseCode = "204", description = "Image successfully deleted")
    @ApiResponse(responseCode = "404", description = "Image not found")
    @DeleteMapping("/product-images/{imageId}")
    public ResponseEntity<Void> deleteImage(@Parameter(description = "ID of the image to delete") @PathVariable Long imageId) {
        imageService.delete(imageId);
        return ResponseEntity.noContent().build();
    }
    @Operation(
            summary = "Set image as main",
            description = "Marks an image as the main image for its associated product"
    )
    @ApiResponse(responseCode = "204", description = "Image marked as main")
    @ApiResponse(responseCode = "404", description = "Image not found")
    @PutMapping("/{id}/set-main")
    public ResponseEntity<Void> setMainImage(@PathVariable Long id) {
        imageService.setMainImage(id);
        return ResponseEntity.noContent().build();
    }
}