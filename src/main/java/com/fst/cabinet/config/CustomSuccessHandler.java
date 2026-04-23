package com.fst.cabinet.config;

import java.io.IOException;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority auth : authorities) {

            String role = auth.getAuthority();

            if (role.equals("ROLE_ADMIN")) {
                response.sendRedirect("/admin/dashboard");
                return;
            }

            if (role.equals("ROLE_MEDECIN")) {
                response.sendRedirect("/medecin/dashboard");
                return;
            }

            if (role.equals("ROLE_SECRETAIRE")) {
                response.sendRedirect("/secretaire/dashboard");
                return;
            }

            if (role.equals("ROLE_PATIENT")) {
                response.sendRedirect("/patient/dashboard");
                return;
            }
        }

        response.sendRedirect("/login?error");
    }
}