package com.medilabo_solutions.diabete.controller;

import com.medilabo_solutions.diabete.service.contracts.IDiabetesRiskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest(controllers = DiabetesRiskController.class)
public class DiabetesRiskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDiabetesRiskService diabetesRiskService;

    @Test
    public void getDiabetesRisk_shouldReturnOk() throws Exception {
        when(diabetesRiskService.evaluateDiabetesRisk(anyLong())).thenReturn("In Danger");

        mockMvc.perform(MockMvcRequestBuilders.get("/diabetes/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("In Danger"));
    }

    @Test
    public void getDiabetesRisk_shouldReturnNotFound() throws Exception {
        when(diabetesRiskService.evaluateDiabetesRisk(anyLong())).thenThrow(new RuntimeException("Patient not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/diabetes/999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
