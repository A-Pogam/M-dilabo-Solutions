package com.medilabo_solutions.patient.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.medilabo_solutions.patient.model.Patient;
import com.medilabo_solutions.patient.service.contracts.IPatientService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPatientService iPatientService;

    private Patient anyPatient = new Patient(1L, "John", "Doe", new Date(), "Male", "123 Main St", "123-456-7890");
    private List<Patient> patients = new ArrayList<>(Arrays.asList(anyPatient, anyPatient, anyPatient));

    @Test
    public void getAllPatients_returnOk() throws Exception {
        when(iPatientService.getAllPatients()).thenReturn(patients);

        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));

        verify(iPatientService, times(1)).getAllPatients();
    }

    @Test
    public void getPatientByName_returnOk() throws Exception {
        when(iPatientService.getPatientByName("John", "Doe")).thenReturn(anyPatient);

        mockMvc.perform(get("/patients/search")
                        .param("firstName", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(iPatientService, times(1)).getPatientByName("John", "Doe");
    }

    @Test
    public void getPatientById_returnOk() throws Exception {
        when(iPatientService.getPatientById(anyLong())).thenReturn(anyPatient);

        mockMvc.perform(get("/patients/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(iPatientService, times(1)).getPatientById(1L);
    }

    @Disabled
    @Test
    public void updatePatient_success() throws Exception {
        when(iPatientService.updatePatient(eq(1L), any(Patient.class))).thenReturn(anyPatient);

        mockMvc.perform(put("/patients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"dateOfBirth\": \"2024-01-01\", \"gender\": \"Male\", \"postalAddress\": \"123 Main St\", \"phoneNumber\": \"123-456-7890\" }"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.dateOfBirth").value(contains("2024-01-01")))
                .andExpect(jsonPath("$.gender").value("Male"))
                .andExpect(jsonPath("$.postalAddress").value("123 Main St"))
                .andExpect(jsonPath("$.phoneNumber").value("123-456-7890"));


        verify(iPatientService, times(1)).updatePatient(eq(1L), anyPatient);
    }


    @Disabled
    @Test
    public void updatePatient_invalidData() throws Exception {
        MvcResult result = mockMvc.perform(put("/patients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"firstName\": null, \"lastName\": null, \"dateOfBirth\": \"2024-01-01\", \"gender\": null, \"postalAddress\": \"123 Main St\", \"phoneNumber\": \"123-456-7890\" }"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

    }
}
