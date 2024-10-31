package com.keyin.travelapi.service;

import com.keyin.travelapi.model.City;
import com.keyin.travelapi.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    private City city;

    @BeforeEach
    void setUp() {
        city = new City();
        city.setId(1L);
        city.setName("San Francisco");
        city.setState("CA");
        city.setPopulation(874_961);
    }

    @Test
    void shouldGetAllCities() {
        List<City> cities = Arrays.asList(city, new City());
        when(cityRepository.findAll()).thenReturn(cities);

        List<City> result = cityService.getAllCities();

        assertThat(result).hasSize(2);
        verify(cityRepository).findAll();
    }

    @Test
    void shouldGetCityById() {
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        Optional<City> result = cityService.getCityById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("San Francisco");
        verify(cityRepository).findById(1L);
    }

    @Test
    void shouldSaveCity() {
        when(cityRepository.save(any(City.class))).thenReturn(city);

        City savedCity = cityService.saveCity(city);

        assertThat(savedCity).isNotNull();
        assertThat(savedCity.getName()).isEqualTo("San Francisco");
        verify(cityRepository).save(city);
    }

    @Test
    void shouldDeleteCity() {
        doNothing().when(cityRepository).deleteById(1L);

        cityService.deleteCity(1L);

        verify(cityRepository).deleteById(1L);
    }
}