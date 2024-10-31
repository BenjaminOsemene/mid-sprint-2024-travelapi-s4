package com.keyin.travelapi.service;

import com.keyin.travelapi.model.Airport;
import com.keyin.travelapi.repository.AirportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AirportServiceTest {

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AirportService airportService;

    private Airport testAirport;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testAirport = new Airport();
        testAirport.setId(1L);
        testAirport.setName("Test Airport");
        testAirport.setCode("TST");
    }

    @Test
    void getAllAirports_shouldReturnListOfAirports() {
        when(airportRepository.findAll()).thenReturn(Arrays.asList(testAirport));

        List<Airport> result = airportService.getAllAirports();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Test Airport");
        verify(airportRepository).findAll();
    }

    @Test
    void getAirportById_shouldReturnAirport_whenAirportExists() {
        when(airportRepository.findById(1L)).thenReturn(Optional.of(testAirport));

        Optional<Airport> result = airportService.getAirportById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Test Airport");
        verify(airportRepository).findById(1L);
    }

    @Test
    void saveAirport_shouldReturnSavedAirport() {
        when(airportRepository.save(any(Airport.class))).thenReturn(testAirport);

        Airport result = airportService.saveAirport(testAirport);

        assertThat(result.getName()).isEqualTo("Test Airport");
        verify(airportRepository).save(testAirport);
    }

    @Test
    void deleteAirport_shouldCallRepositoryDeleteMethod() {
        airportService.deleteAirport(1L);

        verify(airportRepository).deleteById(1L);
    }
}