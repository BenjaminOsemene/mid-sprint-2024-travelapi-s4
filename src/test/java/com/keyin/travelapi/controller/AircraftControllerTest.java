package com.keyin.travelapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.travelapi.model.Aircraft;
import com.keyin.travelapi.model.Passenger;
import com.keyin.travelapi.service.AircraftService;
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

@WebMvcTest(AircraftController.class)
class AircraftControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AircraftService aircraftService;

    @Autowired
    private ObjectMapper objectMapper;

    private Aircraft testAircraft;

    @BeforeEach
    void setUp() {
        testAircraft = new Aircraft();
        testAircraft.setId(1L);
        testAircraft.setType("Boeing 747");
        testAircraft.setAirlineName("Test Airline");
        testAircraft.setNumberOfPassengers(366);
    }

    @Test
    void getAllAircraft_shouldReturnListOfAircraft() throws Exception {
        when(aircraftService.getAllAircraft()).thenReturn(Arrays.asList(testAircraft));

        mockMvc.perform(get("/api/aircraft"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("Boeing 747"))
                .andExpect(jsonPath("$[0].airlineName").value("Test Airline"));

        verify(aircraftService).getAllAircraft();
    }

    @Test
    void getAircraftById_shouldReturnAircraft_whenAircraftExists() throws Exception {
        when(aircraftService.getAircraftById(1L)).thenReturn(Optional.of(testAircraft));

        mockMvc.perform(get("/api/aircraft/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Boeing 747"))
                .andExpect(jsonPath("$.airlineName").value("Test Airline"));

        verify(aircraftService).getAircraftById(1L);
    }

    @Test
    void createAircraft_shouldReturnCreatedAircraft() throws Exception {
        when(aircraftService.saveAircraft(any(Aircraft.class))).thenReturn(testAircraft);

        mockMvc.perform(post("/api/aircraft")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAircraft)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("Boeing 747"))
                .andExpect(jsonPath("$.airlineName").value("Test Airline"));

        verify(aircraftService).saveAircraft(any(Aircraft.class));
    }

    @Test
    void updateAircraft_shouldReturnUpdatedAircraft() throws Exception {
        when(aircraftService.getAircraftById(1L)).thenReturn(Optional.of(testAircraft));
        when(aircraftService.saveAircraft(any(Aircraft.class))).thenReturn(testAircraft);

        mockMvc.perform(put("/api/aircraft/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testAircraft)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Boeing 747"))
                .andExpect(jsonPath("$.airlineName").value("Test Airline"));

        verify(aircraftService).getAircraftById(1L);
        verify(aircraftService).saveAircraft(any(Aircraft.class));
    }

    @Test
    void deleteAircraft_shouldReturnNoContent() throws Exception {
        when(aircraftService.getAircraftById(1L)).thenReturn(Optional.of(testAircraft));
        doNothing().when(aircraftService).deleteAircraft(1L);

        mockMvc.perform(delete("/api/aircraft/1"))
                .andExpect(status().isNoContent());

        verify(aircraftService).getAircraftById(1L);
        verify(aircraftService).deleteAircraft(1L);
    }

    @Test
    void getAircraftPassengers_shouldReturnListOfPassengers() throws Exception {
        Passenger testPassenger = new Passenger();
        testPassenger.setId(1L);
        testPassenger.setFirstName("John");
        testPassenger.setLastName("Doe");
        testAircraft.setPassengers(Arrays.asList(testPassenger));

        when(aircraftService.getAircraftById(1L)).thenReturn(Optional.of(testAircraft));

        mockMvc.perform(get("/api/aircraft/1/passengers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));

        verify(aircraftService).getAircraftById(1L);
    }
}