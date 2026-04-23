package com.fst.cabinet.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;

    private String nom;

    private String prenom;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // PATIENT LINK (only if role = PATIENT)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id", unique = true)
    private Patient patient;

    // MEDECIN LINK (only if role = MEDECIN)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medecin_id", unique = true)
    private Medecin medecin;
}