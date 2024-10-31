package com.keyin.travelapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.travelapi.model.Passenger;
import com.keyin.travelapi.model.Aircraft;
import com.keyin.travelapi.service.PassengerService;
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

@WebMvcTest(PassengerController.class)
class PassengerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PassengerService passengerService;

    @Autowired
    private ObjectMapper objectMapper;

    private Passenger testPassenger;

    @BeforeEach
    void setUp() {
        testPassenger = new Passenger();
        testPassenger.setId(1L);
        testPassenger.setFirstName("John");
        testPassenger.setLastName("Doe");
        testPassenger.setPhoneNumber("1234567890");
    }

    @Test
    void getAllPassengers_shouldReturnListOfPassengers() throws Exception {
        when(passengerService.getAllPassengers()).thenReturn(Arrays.asList(testPassenger));

        mockMvc.perform(get("/api/passengers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));

        verify(passengerService).getAllPassengers();
    }

    @Test
    void getPassengerById_shouldReturnPassenger_whenPassengerExists() throws Exception {
        when(passengerService.getPassengerById(1L)).thenReturn(Optional.of(testPassenger));

        mockMvc.perform(get("/api/passengers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(passengerService).getPassengerById(1L);
    }

    @Test
    void createPassenger_shouldReturnCreatedPassenger() throws Exception {
        when(passengerService.savePassenger(any(Passenger.class))).thenReturn(testPassenger);

        mockMvc.perform(post("/api/passengers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPassenger)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(passengerService).savePassenger(any(Passenger.class));
    }

    @Test
    void updatePassenger_shouldReturnUpdatedPassenger() throws Exception {
        when(passengerService.getPassengerById(1L)).thenReturn(Optional.of(testPassenger));
        when(passengerService.savePassenger(any(Passenger.class))).thenReturn(testPassenger);

        mockMvc.perform(put("/api/passengers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testPassenger)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(passengerService).getPassengerById(1L);
        verify(passengerService).savePassenger(any(Passenger.class));
    }

    @Test
    void deletePassenger_shouldReturnNoContent() throws Exception {
        when(passengerService.getPassengerById(1L)).thenReturn(Optional.of(testPassenger));
        doNothing().when(passengerService).deletePassenger(1L);

        mockMvc.perform(delete("/api/passengers/1"))
                .andExpect(status().isNoContent());

        verify(passengerService).getPassengerById(1L);
        verify(passengerService).deletePassenger(1L);
    }

    @Test
    void getPassengerAircraft_shouldReturnListOfAircraft() throws Exception {
        Aircraft testAircraft = new Aircraft();
        testAircraft.setId(1L);
        testAircraft.setType("Boeing 747");
        testPassenger.setAircraft(Arrays.asList(testAircraft));

        when(passengerService.getPassengerById(1L)).thenReturn(Optional.of(testPassenger));

        mockMvc.perform(get("/api/passengers/1/aircraft"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("Boeing 747"));

        verify(passengerService).getPassengerById(1L);
    }
}