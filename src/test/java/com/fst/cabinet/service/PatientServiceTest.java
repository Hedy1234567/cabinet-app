package com.fst.cabinet.service;

import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.repository.PatientRepository;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    public PatientServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePatient_success() {
        Patient patient = new Patient();
        patient.setCin("12345678");

        when(patientRepository.findByCin("12345678"))
                .thenReturn(Optional.empty());

        when(patientRepository.save(any(Patient.class)))
                .thenReturn(patient);

        Patient result = patientService.savePatient(patient);

        assertNotNull(result);
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void testSavePatient_duplicateCin_shouldThrow() {
        Patient existing = new Patient();
        existing.setId(1L);
        existing.setCin("12345678");

        Patient newPatient = new Patient();
        newPatient.setCin("12345678");

        when(patientRepository.findByCin("12345678"))
                .thenReturn(Optional.of(existing));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientService.savePatient(newPatient);
        });

        assertEquals("CIN already exists", exception.getMessage());
    }
}