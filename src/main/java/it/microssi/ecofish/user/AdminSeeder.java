package it.microssi.ecofish.user;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("adminpass")); // hashata!
            admin.setEmail("admin@example.com");
            admin.setEnabled(true);
            admin.setRoles(Set.of("ROLE_ADMIN"));

            userRepository.save(admin);
            System.out.println("✅ Admin user created: admin/adminpass");
        } else {
            System.out.println("✅ Admin user already exists.");
        }
    }
}