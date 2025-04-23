package it.microssi.ecofish.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllProductsEndpoint() throws Exception {
        // Test that the /api/products endpoint returns 200 OK
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk());

        System.out.println("[DEBUG_LOG] GET /api/products endpoint returned 200 OK");
    }

    @Test
    public void testPostProductEndpoint() throws Exception {
        // Create a product DTO for testing
        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(10.0f);
        productDTO.setCategory("Test Category");
        productDTO.setStockQuantity(10);

        // Test that the POST /api/products endpoint returns 200 OK
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk());

        System.out.println("[DEBUG_LOG] POST /api/products endpoint returned 200 OK");
    }

    @Test
    public void testPutProductEndpoint() throws Exception {
        // Create a product DTO for testing
        ProductDTO productDTO = new ProductDTO();
        productDTO.setTitle("Updated Product");
        productDTO.setDescription("Updated Description");
        productDTO.setPrice(20.0f);
        productDTO.setCategory("Updated Category");
        productDTO.setStockQuantity(20);

        // Test that the PUT /api/products/1 endpoint returns 200 OK or 404 Not Found
        try {
            mockMvc.perform(put("/api/products/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productDTO)))
                    .andExpect(status().isOk());
            System.out.println("[DEBUG_LOG] PUT /api/products/1 endpoint returned 200 OK");
        } catch (AssertionError e) {
            // If not 200 OK, check if it's 404 Not Found
            mockMvc.perform(put("/api/products/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(productDTO)))
                    .andExpect(status().isNotFound());
            System.out.println("[DEBUG_LOG] PUT /api/products/1 endpoint returned 404 Not Found");
        }

        System.out.println("[DEBUG_LOG] PUT /api/products/1 endpoint returned expected status");
    }

    @Test
    public void testDeleteProductEndpoint() throws Exception {
        // Test that the DELETE /api/products/1 endpoint returns 200 OK
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isOk());

        System.out.println("[DEBUG_LOG] DELETE /api/products/1 endpoint returned 200 OK");
    }

    @Test
    public void testExactUserRequest() throws Exception {
        // This test mimics the exact request the user is making
        String jsonPayload = """
                {
                  "title": "Fresh Atlantic Salmon",
                  "description": "Premium quality Atlantic salmon, sustainably farmed and harvested daily. Rich in omega-3 fatty acids and perfect for grilling or baking.",
                  "price": 12.99,
                  "imageUrl": "https://ecofish.com/images/products/atlantic-salmon.jpg",
                  "category": "Fish",
                  "stockQuantity": 50
                }""";

        // Test that the POST /api/products endpoint returns 200 OK with the exact payload
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isOk());

        System.out.println("[DEBUG_LOG] POST /api/products with exact user payload returned 200 OK");
    }
}
