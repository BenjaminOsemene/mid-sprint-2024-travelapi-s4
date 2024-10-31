package com.keyin.travelapi.service;

import com.keyin.travelapi.model.Passenger;
import com.keyin.travelapi.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {
    private static final Logger logger = LoggerFactory.getLogger(PassengerService.class);
    private PassengerRepository passengerRepository = null;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }


    public List<Passenger>getAllPassengers() {
        logger.info("Fetching all passengers");
        return passengerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Passenger> getPassengerById(Long id) {
        logger.info("Fetching passenger with id: {}", id);
        return passengerRepository.findById(id);
    }

    @Transactional
    public Passenger savePassenger(Passenger passenger) {
        logger.info("Saving passenger: {}", passenger);
        return passengerRepository.save(passenger);
    }

    @Transactional
    public void deletePassenger(Long id) {
        logger.info("Deleting passenger with id: {}", id);
        passengerRepository.deleteById(id);
    }

    public Object addAircraftToPassenger(long l, long l1) {
        return null;
    }

    public Object setPassengerCity(long l, long l1) {
        return null;
    }

    public Object getCityForPassenger(long l) {
        return null;
    }

    public Object getAircraftForPassenger(long l) {
        return null;
    }
}