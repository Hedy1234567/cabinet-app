package com.fst.cabinet.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fst.cabinet.entity.Medecin;
import com.fst.cabinet.repository.MedecinRepository;

@ExtendWith(MockitoExtension.class)
class MedecinServiceTest {

    @Mock
    private MedecinRepository medecinRepository;

    @InjectMocks
    private MedecinService medecinService;

    private Medecin medecin;

    @BeforeEach
    void setUp() {
        medecin = new Medecin();
        medecin.setId(1L);
        medecin.setNom("Ben Ali");
        medecin.setPrenom("Sami");
        medecin.setSpecialite("Cardiologie");
        medecin.setNumeroOrdre("ORD123");
        medecin.setTelephone("55443322");
        medecin.setEmail("medecin@test.com");
        medecin.setActif(true);
    }

    @Test
    void shouldSaveMedecinSuccessfully() {
        medecin.setId(null);

        when(medecinRepository.existsByNumeroOrdre("ORD123")).thenReturn(false);
        when(medecinRepository.save(any(Medecin.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Medecin savedMedecin = medecinService.save(medecin);

        assertNotNull(savedMedecin);
        assertEquals("ORD123", savedMedecin.getNumeroOrdre());
        verify(medecinRepository).save(any(Medecin.class));
    }

    @Test
    void shouldThrowExceptionWhenNumeroOrdreAlreadyExists() {
        medecin.setId(null);

        when(medecinRepository.existsByNumeroOrdre("ORD123")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            medecinService.save(medecin);
        });

        assertEquals("Numéro d'ordre déjà existant", exception.getMessage());
        verify(medecinRepository, never()).save(any(Medecin.class));
    }

    @Test
    void shouldUpdateMedecinSuccessfully() {
        Medecin existing = new Medecin();
        existing.setId(1L);
        existing.setNom("Old");
        existing.setPrenom("Old");
        existing.setSpecialite("Old");
        existing.setNumeroOrdre("ORD123");
        existing.setTelephone("11111111");
        existing.setEmail("old@test.com");
        existing.setActif(false);

        when(medecinRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(medecinRepository.existsByNumeroOrdreAndIdNot("ORD123", 1L)).thenReturn(false);
        when(medecinRepository.save(any(Medecin.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Medecin updated = medecinService.update(1L, medecin);

        assertEquals("Ben Ali", updated.getNom());
        assertEquals("Sami", updated.getPrenom());
        assertEquals("Cardiologie", updated.getSpecialite());
        assertEquals("ORD123", updated.getNumeroOrdre());
        assertTrue(updated.isActif());
        verify(medecinRepository).save(any(Medecin.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingWithDuplicateNumeroOrdre() {
        when(medecinRepository.findById(1L)).thenReturn(Optional.of(new Medecin()));
        when(medecinRepository.existsByNumeroOrdreAndIdNot("ORD123", 1L)).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            medecinService.update(1L, medecin);
        });

        assertEquals("Numéro d'ordre déjà existant", exception.getMessage());
    }

    @Test
    void shouldReturnAllMedecins() {
        when(medecinRepository.findAll()).thenReturn(List.of(medecin));

        List<Medecin> result = medecinService.getAll();

        assertEquals(1, result.size());
        verify(medecinRepository).findAll();
    }

    @Test
    void shouldThrowExceptionWhenMedecinNotFound() {
        when(medecinRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            medecinService.getById(99L);
        });

        assertEquals("Médecin not found", exception.getMessage());
    }
}