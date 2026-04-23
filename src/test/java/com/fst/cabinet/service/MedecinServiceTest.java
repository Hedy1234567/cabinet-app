package com.fst.cabinet.service;

import com.fst.cabinet.entity.Medecin;
import com.fst.cabinet.repository.MedecinRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedecinServiceTest {

    @Mock
    private MedecinRepository medecinRepository;

    @InjectMocks
    private MedecinService medecinService;

    public MedecinServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveMedecin_success() {
        Medecin medecin = new Medecin();
        medecin.setNumeroOrdre("ORD123");

        when(medecinRepository.findByNumeroOrdre("ORD123"))
                .thenReturn(Optional.empty());

        when(medecinRepository.save(any(Medecin.class)))
                .thenReturn(medecin);

        Medecin result = medecinService.saveMedecin(medecin);

        assertNotNull(result);
        verify(medecinRepository, times(1)).save(medecin);
    }

    @Test
    void testSaveMedecin_duplicateNumeroOrdre_shouldThrow() {
        Medecin existing = new Medecin();
        existing.setId(1L);
        existing.setNumeroOrdre("ORD123");

        Medecin newMedecin = new Medecin();
        newMedecin.setNumeroOrdre("ORD123");

        when(medecinRepository.findByNumeroOrdre("ORD123"))
                .thenReturn(Optional.of(existing));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            medecinService.saveMedecin(newMedecin);
        });

        assertEquals("Numero ordre already exists", exception.getMessage());
    }

    @Test
    void testDeleteMedecin_success() {
        when(medecinRepository.existsById(1L)).thenReturn(true);

        medecinService.deleteMedecin(1L);

        verify(medecinRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteMedecin_notFound_shouldThrow() {
        when(medecinRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            medecinService.deleteMedecin(1L);
        });

        assertEquals("Medecin not found", exception.getMessage());
    }
}