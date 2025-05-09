package it.microssi.ecofish.auth;

import it.microssi.ecofish.user.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public AuthController(
            AuthenticationManager authManager,
            JwtService jwtService,
            CustomUserDetailsService userDetailsService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        // Autentico l'utente
        var auth = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );
        authManager.authenticate(auth);

        // Carico i dettagli dell'utente per includere i ruoli
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        return jwtService.generateToken(userDetails);
    }

}