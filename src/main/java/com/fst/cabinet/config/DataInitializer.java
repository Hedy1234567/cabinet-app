package com.fst.cabinet.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fst.cabinet.entity.AppUser;
import com.fst.cabinet.entity.Role;
import com.fst.cabinet.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // 🔥 CHECK IF ADMIN EXISTS
        boolean adminExists = userRepository.findByUsername("admin").isPresent();

        if (!adminExists) {

            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@cabinet.com");
            admin.setNom("Admin");
            admin.setPrenom("System");
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);

            System.out.println("ADMIN CREATED: admin / admin123");
        }
    }
}