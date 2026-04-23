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

        // ✅ ADMIN
        if (userRepository.findByUsername("admin").isEmpty()) {

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

        // ✅ SECRETAIRE
        if (userRepository.findByUsername("secretaire").isEmpty()) {

            AppUser sec = new AppUser();
            sec.setUsername("secretaire");
            sec.setPassword(passwordEncoder.encode("sec123"));
            sec.setEmail("sec@cabinet.com");
            sec.setNom("Secretaire");
            sec.setPrenom("Cabinet");
            sec.setRole(Role.SECRETAIRE);

            userRepository.save(sec);

            System.out.println("SECRETAIRE CREATED: secretaire / sec123");
        }
        // 🔥 CHECK IF MEDECIN EXISTS
boolean medecinExists = userRepository.findByUsername("medecin").isPresent();

if (!medecinExists) {

    AppUser medecin = new AppUser();
    medecin.setUsername("medecin");
    medecin.setPassword(passwordEncoder.encode("med123"));
    medecin.setEmail("medecin@cabinet.com");
    medecin.setNom("Doctor");
    medecin.setPrenom("Test");
    medecin.setRole(Role.MEDECIN);

    userRepository.save(medecin);

    System.out.println("MEDECIN CREATED: medecin / med123");
}
    }
}