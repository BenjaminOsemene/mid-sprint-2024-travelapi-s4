package com.keyin.travelapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.travelapi.model.Airport;
import com.keyin.travelapi.model.Aircraft;
import com.keyin.travelapi.service.AirportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AirportController.class)
class AirportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirportService airportService;

    @Autowired
    private ObjectMapper objectMapper;

    private Airport testAirport;

    @BeforeEach
    void setUp() {
        testAirport = new Airport();
        testAirport.setId(1L);
        testAirport.setName("Test Airport");
        testAirport.setCode("TST");
    }

    @Test
    void getAllAirports_shouldReturnListOfAirports() throws Exception {
        when(airportService.getAllAirports()).thenReturn(Arrays.asList(testAirport));

        mockMvc.perform(get("/api/airports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Airport"))
                .andExpect(jsonPath("$[0].code").value("TST"));

        verify(airportService).getAllAirports();
    }

    @Test
    void getAirportById_shouldReturnAirport_whenAirportExists() throws Exception {
        when(airportService.getAirportById(1L)).thenReturn(Optional.of(testAirport));

        mockMvc.perform(get("/api/airports/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Airport"))
                .andExpect(jsonPath("$.code").value("TST"));

        verify(airportService).getAirportById(1L);
    }

    @Test
    void createAirport_shouldReturnCreatedAirport() throws Exception {
        when(airportService.saveAirport(any(Airport.class))).thenReturn(testAirport);

        mockMvc.perform(post("/api/airports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAirport)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Airport"))
                .andExpect(jsonPath("$.code").value("TST"));

        verify(airportService).saveAirport(any(Airport.class));
    }

    @Test
    void updateAirport_shouldReturnUpdatedAirport() throws Exception {
        when(airportService.getAirportById(1L)).thenReturn(Optional.of(testAirport));
        when(airportService.saveAirport(any(Airport.class))).thenReturn(testAirport);

        mockMvc.perform(put("/api/airports/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAirport)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Airport"))
                .andExpect(jsonPath("$.code").value("TST"));

        verify(airportService).getAirportById(1L);
        verify(airportService).saveAirport(any(Airport.class));
    }

    @Test
    void deleteAirport_shouldReturnNoContent() throws Exception {
        when(airportService.getAirportById(1L)).thenReturn(Optional.of(testAirport));
        doNothing().when(airportService).deleteAirport(1L);

        mockMvc.perform(delete("/api/airports/1"))
                .andExpect(status().isNoContent());

        verify(airportService).getAirportById(1L);
        verify(airportService).deleteAirport(1L);
    }

    @Test
    void getAirportAircraft_shouldReturnListOfAircraft() throws Exception {
        Aircraft testAircraft = new Aircraft();
        testAircraft.setId(1L);
        testAircraft.setType("Boeing 747");
        testAirport.setAircraft(Arrays.asList(testAircraft));

        when(airportService.getAirportById(1L)).thenReturn(Optional.of(testAirport));

        mockMvc.perform(get("/api/airports/1/aircraft"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("Boeing 747"));

        verify(airportService).getAirportById(1L);
    }
}