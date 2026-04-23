package com.fst.cabinet.service;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.fst.cabinet.entity.AppUser;
import com.fst.cabinet.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())

                // ✅ FIXED ROLE MAPPING (IMPORTANT)
                .authorities("ROLE_" + user.getRole().name())

                .build();
    }
}