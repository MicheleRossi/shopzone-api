package it.microssi.ecofish.productimage;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.microssi.ecofish.auth.JwtService;
import it.microssi.ecofish.product.Product;
import it.microssi.ecofish.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository imageRepository;

    private String adminToken;
    private Long testProductId;
    private Long testImageId;

    @BeforeEach
    public void setup() throws Exception {
        // Get user token for authenticated requests
        UserDetails userDetails = userDetailsService.loadUserByUsername("user");
        adminToken = jwtService.generateToken(userDetails);

        System.out.println("[DEBUG_LOG] Admin token generated for tests: " + adminToken);

        // Find an existing product or create one for testing
        Optional<Product> existingProduct = productRepository.findAll().stream().findFirst();
        if (existingProduct.isPresent()) {
            testProductId = existingProduct.get().getId();
            System.out.println("[DEBUG_LOG] Using existing product with ID: " + testProductId);
        } else {
            System.out.println("[DEBUG_LOG] No existing products found for testing");
            testProductId = 1L; // Fallback to ID 1
        }

        // Find an existing image or use ID 1 for testing
        Optional<ProductImage> existingImage = imageRepository.findAll().stream().findFirst();
        if (existingImage.isPresent()) {
            testImageId = existingImage.get().getId();
            System.out.println("[DEBUG_LOG] Using existing image with ID: " + testImageId);
        } else {
            System.out.println("[DEBUG_LOG] No existing images found for testing");
            testImageId = 1L; // Fallback to ID 1
        }
    }

    @Test
    public void testUploadImage() throws Exception {
        // Create a mock multipart file for testing
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test-image.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );

        // Test that the POST /api/products/{productId}/images endpoint returns 200 OK when authenticated
        mockMvc.perform(multipart("/api/products/" + testProductId + "/images")
                        .file(file)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());

        System.out.println("[DEBUG_LOG] POST /api/products/" + testProductId + "/images endpoint returned 200 OK");

        // Test that the endpoint returns 403 Forbidden when not authenticated
        mockMvc.perform(multipart("/api/products/" + testProductId + "/images")
                        .file(file))
                .andExpect(status().isForbidden());

        System.out.println("[DEBUG_LOG] POST /api/products/" + testProductId + "/images endpoint without authentication returned 403 Forbidden");
    }

//    @Test
//    public void testDeleteImage() throws Exception {
//        // Test that the DELETE /api/product-images/{imageId} endpoint returns 204 No Content when authenticated
//        mockMvc.perform(delete("/api/product-images/" + testImageId)
//                        .header("Authorization", "Bearer " + adminToken))
//                .andExpect(status().isNoContent());
//
//        System.out.println("[DEBUG_LOG] DELETE /api/product-images/" + testImageId + " endpoint returned 204 No Content");
//
//        // Test that the endpoint returns 403 Forbidden when not authenticated
//        mockMvc.perform(delete("/api/product-images/" + testImageId))
//                .andExpect(status().isForbidden());
//
//        System.out.println("[DEBUG_LOG] DELETE /api/product-images/" + testImageId + " endpoint without authentication returned 403 Forbidden");
//    }
}