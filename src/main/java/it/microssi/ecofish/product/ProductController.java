package it.microssi.ecofish.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Product management APIs")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @Operation(
            summary = "Get all products",
            description = "Retrieves a list of all products"
    )
    @ApiResponse(responseCode = "200", description = "List of products successfully retrieved")
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.get();
    }

    @Operation(
            summary = "Get product by ID",
            description = "Retrieves a specific product by its ID"
    )
    @ApiResponse(responseCode = "200", description = "Product found")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(
            @Parameter(description = "ID of the product to retrieve") @PathVariable Long id) {
        return Optional.ofNullable(productService.get(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create new product",
            description = "Creates a new product in the system"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "201", description = "Product successfully created")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO savedProduct = productService.save(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @Operation(
            summary = "Update product",
            description = "Updates an existing product's information"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "200", description = "Product successfully updated")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(
            @Parameter(description = "ID of the product to update") @PathVariable Long id,
            @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return updatedProduct != null ?
                ResponseEntity.ok(updatedProduct) :
                ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Delete product",
            description = "Deletes a product from the system"
    )
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponse(responseCode = "200", description = "Product successfully deleted")
    @ApiResponse(responseCode = "403", description = "Access denied")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@Parameter(description = "ID of the product to delete") @PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }
}