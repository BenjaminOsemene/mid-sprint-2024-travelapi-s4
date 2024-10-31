
package com.keyin.travelapi.service;

import com.keyin.travelapi.model.Aircraft;
import com.keyin.travelapi.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class AircraftService {

    private static final Logger logger = LoggerFactory.getLogger(AircraftService.class);
    private final AircraftRepository aircraftRepository;

    @Autowired
    public AircraftService(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    @Transactional(readOnly = true)
    public List<Aircraft> getAllAircraft() {
        logger.info("Fetching all aircraft with pagination");
        return aircraftRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Aircraft> getAircraftById(Long id) {
        logger.info("Fetching aircraft with id: {}", id);
        return aircraftRepository.findById(id);
    }

    @Transactional
    public Aircraft saveAircraft(Aircraft aircraft) {
        logger.info("Saving aircraft: {}", aircraft);
        return aircraftRepository.save(aircraft);
    }

    @Transactional
    public void deleteAircraft(Long id) {
        logger.info("Deleting aircraft with id: {}", id);
        aircraftRepository.deleteById(id);
    }

    public Object addAirportToAircraft(long l, long l1) {
        return null;
    }

    public Object getPassengersForAircraft(long l) {
        return null;
    }

    public Object getAirportsForAircraft(long l) {
        return null;
    }

    public Object addPassengerToAircraft(long l, long l1) {
        return null;
    }
}