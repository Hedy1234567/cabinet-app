package com.fst.cabinet.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.fst.cabinet.entity.Patient;
import com.fst.cabinet.repository.PatientRepository;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = Patient.builder()
                .id(1L)
                .cin("12345678")
                .nom("Medddeb")
                .prenom("Mohamed Hedi")
                .dateNaissance(LocalDate.of(2000, 1, 1))
                .telephone("55634578")
                .email("test@gmail.com")
                .antecedents("Aucun")
                .build();
    }

    @Test
    void shouldSavePatientSuccessfully() {
        patient.setId(null);

        when(patientRepository.findByCin(patient.getCin())).thenReturn(Optional.empty());
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Patient savedPatient = patientService.savePatient(patient);

        assertNotNull(savedPatient);
        assertEquals("12345678", savedPatient.getCin());
        assertNotNull(savedPatient.getDateCreation());
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    void shouldThrowExceptionWhenCinAlreadyExistsForNewPatient() {
        Patient existingPatient = Patient.builder()
                .id(2L)
                .cin("12345678")
                .build();

        patient.setId(null);

        when(patientRepository.findByCin("12345678")).thenReturn(Optional.of(existingPatient));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientService.savePatient(patient);
        });

        assertEquals("CIN déjà existant", exception.getMessage());
        verify(patientRepository, never()).save(any(Patient.class));
    }

    @Test
    void shouldUpdatePatientAndKeepDateCreation() {
        LocalDateTime oldDateCreation = LocalDateTime.of(2026, 4, 21, 10, 0);

        Patient existingPatient = Patient.builder()
                .id(1L)
                .cin("12345678")
                .nom("Old")
                .prenom("Name")
                .dateCreation(oldDateCreation)
                .build();

        patient.setId(1L);

        when(patientRepository.findByCin(patient.getCin())).thenReturn(Optional.of(existingPatient));
        when(patientRepository.findById(1L)).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Patient updatedPatient = patientService.savePatient(patient);

        assertEquals(oldDateCreation, updatedPatient.getDateCreation());
        verify(patientRepository).save(any(Patient.class));
    }

    @Test
    void shouldReturnAllPatientsWhenKeywordIsEmpty() {
        when(patientRepository.findAll()).thenReturn(List.of(patient));

        List<Patient> result = patientService.searchPatients("");

        assertEquals(1, result.size());
        verify(patientRepository).findAll();
    }

    @Test
    void shouldSearchPatientsByKeyword() {
        when(patientRepository.findByNomContainingIgnoreCaseOrCinContainingIgnoreCaseOrTelephoneContainingIgnoreCase(
                "med", "med", "med"))
                .thenReturn(List.of(patient));

        List<Patient> result = patientService.searchPatients("med");

        assertEquals(1, result.size());
        verify(patientRepository)
                .findByNomContainingIgnoreCaseOrCinContainingIgnoreCaseOrTelephoneContainingIgnoreCase(
                        "med", "med", "med");
    }

    @Test
    void shouldThrowExceptionWhenPatientNotFound() {
        when(patientRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patientService.getPatientById(99L);
        });

        assertEquals("Patient introuvable", exception.getMessage());
    }
}