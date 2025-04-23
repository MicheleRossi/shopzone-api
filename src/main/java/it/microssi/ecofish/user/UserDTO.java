package it.microssi.ecofish.user;

import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<String> roles = new HashSet<>();
    private boolean enabled = true;
}