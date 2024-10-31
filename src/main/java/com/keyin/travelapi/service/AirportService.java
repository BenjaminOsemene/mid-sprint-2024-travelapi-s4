package com.keyin.travelapi.service;

import com.keyin.travelapi.model.Airport;
import com.keyin.travelapi.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class AirportService {
    private static final Logger logger = LoggerFactory.getLogger(AirportService.class);
    private final AirportRepository airportRepository;

    @Autowired
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Transactional(readOnly = true)
    public List<Airport> getAllAirports() {
        logger.info("Fetching all airports with pagination");
        return airportRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Airport> getAirportById(Long id) {
        logger.info("Fetching airport with id: {}", id);
        return airportRepository.findById(id);
    }

    @Transactional
    public Airport saveAirport(Airport airport) {
        logger.info("Saving airport: {}", airport);
        return airportRepository.save(airport);
    }

    @Transactional
    public void deleteAirport(Long id) {
        logger.info("Deleting airport with id: {}", id);
        airportRepository.deleteById(id);
    }

    public Object setAirportCity(long l, long l1) {
        return null;
    }
}