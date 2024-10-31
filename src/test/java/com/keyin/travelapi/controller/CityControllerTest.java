package com.keyin.travelapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.travelapi.model.City;
import com.keyin.travelapi.service.CityService;
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

@WebMvcTest(CityController.class)
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityService cityService;

    @Autowired
    private ObjectMapper objectMapper;

    private City city;

    @BeforeEach
    void setUp() {
        city = new City();
        city.setId(1L);
        city.setName("Test City");
        city.setState("Test State");
        city.setPopulation(100000);
    }

    @Test
    void shouldGetAllCities() throws Exception {
        when(cityService.getAllCities()).thenReturn(Arrays.asList(city));

        mockMvc.perform(get("/api/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test City"))
                .andExpect(jsonPath("$[0].state").value("Test State"));

        verify(cityService).getAllCities();
    }

    @Test
    void shouldGetCityById() throws Exception {
        when(cityService.getCityById(1L)).thenReturn(Optional.of(city));

        mockMvc.perform(get("/api/cities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test City"))
                .andExpect(jsonPath("$.state").value("Test State"));

        verify(cityService).getCityById(1L);
    }

    @Test
    void shouldReturn404WhenCityNotFound() throws Exception {
        when(cityService.getCityById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/cities/1"))
                .andExpect(status().isNotFound());

        verify(cityService).getCityById(1L);
    }

    @Test
    void shouldCreateCity() throws Exception {
        when(cityService.saveCity(any(City.class))).thenReturn(city);

        mockMvc.perform(post("/api/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(city)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test City"))
                .andExpect(jsonPath("$.state").value("Test State"));

        verify(cityService).saveCity(any(City.class));
    }

    @Test
    void shouldUpdateCity() throws Exception {
        when(cityService.getCityById(1L)).thenReturn(Optional.of(city));
        when(cityService.saveCity(any(City.class))).thenReturn(city);

        mockMvc.perform(put("/api/cities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(city)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test City"))
                .andExpect(jsonPath("$.state").value("Test State"));

        verify(cityService).getCityById(1L);
        verify(cityService).saveCity(any(City.class));
    }

    @Test
    void shouldDeleteCity() throws Exception {
        when(cityService.getCityById(1L)).thenReturn(Optional.of(city));
        doNothing().when(cityService).deleteCity(1L);

        mockMvc.perform(delete("/api/cities/1"))
                .andExpect(status().isNoContent());

        verify(cityService).getCityById(1L);
        verify(cityService).deleteCity(1L);
    }

    @Test
    void shouldReturn404WhenDeletingNonExistentCity() throws Exception {
        when(cityService.getCityById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/cities/1"))
                .andExpect(status().isNotFound());

        verify(cityService).getCityById(1L);
        verify(cityService, never()).deleteCity(1L);
    }
}