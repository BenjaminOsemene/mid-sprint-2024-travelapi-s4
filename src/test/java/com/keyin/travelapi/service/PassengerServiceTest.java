package com.keyin.travelapi.service;

import com.keyin.travelapi.model.Passenger;
import com.keyin.travelapi.repository.PassengerRepository;
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

class PassengerServiceTest {

    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private PassengerService passengerService;

    private Passenger testPassenger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testPassenger = new Passenger();
        testPassenger.setId(1L);
        testPassenger.setFirstName("John");
        testPassenger.setLastName("Doe");
        testPassenger.setPhoneNumber("1234567890");
    }

    @Test
    void getAllPassengers_shouldReturnListOfPassengers() {
        when(passengerRepository.findAll()).thenReturn(Arrays.asList(testPassenger));

        List<Passenger> result = passengerService.getAllPassengers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFirstName()).isEqualTo("John");
        verify(passengerRepository).findAll();
    }

    @Test
    void getPassengerById_shouldReturnPassenger_whenPassengerExists() {
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(testPassenger));

        Optional<Passenger> result = passengerService.getPassengerById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().getFirstName()).isEqualTo("John");
        verify(passengerRepository).findById(1L);
    }

    @Test
    void savePassenger_shouldReturnSavedPassenger() {
        when(passengerRepository.save(any(Passenger.class))).thenReturn(testPassenger);

        Passenger result = passengerService.savePassenger(testPassenger);

        assertThat(result.getFirstName()).isEqualTo("John");
        verify(passengerRepository).save(testPassenger);
    }

    @Test
    void deletePassenger_shouldCallRepositoryDeleteMethod() {
        passengerService.deletePassenger(1L);

        verify(passengerRepository).deleteById(1L);
    }
}