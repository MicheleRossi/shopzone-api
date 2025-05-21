package it.microssi.ecofish.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.microssi.ecofish.auth.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    private String adminToken;

    @BeforeEach
    public void setup() throws Exception {
        // Get user token for authenticated requests
        UserDetails userDetails = userDetailsService.loadUserByUsername("user");
        adminToken = jwtService.generateToken(userDetails);

        System.out.println("[DEBUG_LOG] Admin token generated for tests: " + adminToken);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        // Test that the /api/users endpoint returns 200 OK when authenticated
        mockMvc.perform(get("/api/users")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());

        System.out.println("[DEBUG_LOG] GET /api/users endpoint returned 200 OK");
    }

    @Test
    public void testGetUserById() throws Exception {
        // Test that the /api/users/1 endpoint returns 200 OK when authenticated
        // This assumes user with ID 1 exists
        mockMvc.perform(get("/api/users/1")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());

        System.out.println("[DEBUG_LOG] GET /api/users/1 endpoint returned 200 OK");

        // Test that the /api/users/999 endpoint returns 404 Not Found
        mockMvc.perform(get("/api/users/999")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNotFound());

        System.out.println("[DEBUG_LOG] GET /api/users/999 endpoint returned 404 Not Found");
    }

//    @Test
//    public void testCreateUser() throws Exception {
//        // Create a user DTO for testing
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername("testuser");
//        userDTO.setPassword("password");
//        userDTO.setEmail("test@example.com");
//        userDTO.getRoles().add("USER");
//
//        // Test that the POST /api/users endpoint returns 200 OK when authenticated
//        mockMvc.perform(post("/api/users")
//                .header("Authorization", "Bearer " + adminToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username").value("testuser"))
//                .andExpect(jsonPath("$.email").value("test@example.com"));
//
//        System.out.println("[DEBUG_LOG] POST /api/users endpoint returned 200 OK");
//    }
//
//    @Test
//    public void testUpdateUser() throws Exception {
//        // First create a user to update
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername("updateuser");
//        userDTO.setPassword("password");
//        userDTO.setEmail("update@example.com");
//        userDTO.getRoles().add("USER");
//
//        MvcResult result = mockMvc.perform(post("/api/users")
//                .header("Authorization", "Bearer " + adminToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userDTO)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        // Extract the ID of the created user
//        UserDTO createdUser = objectMapper.readValue(
//                result.getResponse().getContentAsString(), UserDTO.class);
//        Long userId = createdUser.getId();
//
//        // Update the user
//        userDTO.setEmail("updated@example.com");
//
//        // Test that the PUT /api/users/{id} endpoint returns 200 OK when authenticated
//        mockMvc.perform(put("/api/users/" + userId)
//                .header("Authorization", "Bearer " + adminToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.email").value("updated@example.com"));
//
//        System.out.println("[DEBUG_LOG] PUT /api/users/" + userId + " endpoint returned 200 OK");
//    }

    @Test
    public void testDeleteUser() throws Exception {
        // First create a user to delete
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("deleteuser");
        userDTO.setPassword("password");
        userDTO.setEmail("delete@example.com");
        userDTO.getRoles().add("USER");

        MvcResult result = mockMvc.perform(post("/api/users")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andReturn();

        // Extract the ID of the created user
        UserDTO createdUser = objectMapper.readValue(
                result.getResponse().getContentAsString(), UserDTO.class);
        Long userId = createdUser.getId();

        // Test that the DELETE /api/users/{id} endpoint returns 200 OK when authenticated
        mockMvc.perform(delete("/api/users/" + userId)
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());

        System.out.println("[DEBUG_LOG] DELETE /api/users/" + userId + " endpoint returned 200 OK");

        // Verify the user is deleted
        mockMvc.perform(get("/api/users/" + userId)
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNotFound());

        System.out.println("[DEBUG_LOG] GET /api/users/" + userId + " after deletion returned 404 Not Found");
    }
}
