package com.fst.cabinet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fst.cabinet.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final CustomSuccessHandler customSuccessHandler;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder,
                          CustomSuccessHandler customSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.customSuccessHandler = customSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth

                // PUBLIC PAGES
                .requestMatchers("/", "/welcome", "/login", "/signup").permitAll()

                // PATIENTS (ALL ROLES EXCEPT PATIENT USER)
                .requestMatchers("/patients/**")
                    .hasAnyRole("ADMIN", "SECRETAIRE", "MEDECIN")

                // MEDECINS (NO MEDECIN ACCESS HERE)
                .requestMatchers("/medecins/**")
                    .hasAnyRole("ADMIN", "SECRETAIRE")

                // ADMIN ONLY
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // everything else requires login
                .anyRequest().authenticated()
            )

            // LOGIN CONFIG
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(customSuccessHandler)
                .permitAll()
            )

            // LOGOUT CONFIG
            .logout(logout -> logout
                .logoutSuccessUrl("/login")
                .permitAll()
            )

            // OPTIONAL (clean 403 page)
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/access-denied")
            );

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }
}