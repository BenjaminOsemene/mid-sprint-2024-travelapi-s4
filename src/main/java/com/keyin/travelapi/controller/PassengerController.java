package com.keyin.travelapi.controller;

import com.keyin.travelapi.model.Passenger;
import com.keyin.travelapi.model.Aircraft;
import com.keyin.travelapi.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        List<Passenger> passengers = passengerService.getAllPassengers();
        return new ResponseEntity<>(passengers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
        return passengerService.getPassengerById(id)
                .map(passenger -> new ResponseEntity<>(passenger, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Passenger> createPassenger(@RequestBody Passenger passenger) {
        Passenger createdPassenger = passengerService.savePassenger(passenger);
        return new ResponseEntity<>(createdPassenger, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long id, @RequestBody Passenger passengerDetails) {
        return passengerService.getPassengerById(id)
                .map(existingPassenger -> {
                    existingPassenger.setFirstName(passengerDetails.getFirstName());
                    existingPassenger.setLastName(passengerDetails.getLastName());
                    existingPassenger.setPhoneNumber(passengerDetails.getPhoneNumber());
                    existingPassenger.setCity(passengerDetails.getCity());
                    Passenger updatedPassenger = passengerService.savePassenger(existingPassenger);
                    return new ResponseEntity<>(updatedPassenger, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        return passengerService.getPassengerById(id)
                .map(passenger -> {
                    passengerService.deletePassenger(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/aircraft")
    public ResponseEntity<List<Aircraft>> getPassengerAircraft(@PathVariable Long id) {
        return passengerService.getPassengerById(id)
                .map(passenger -> new ResponseEntity<>(passenger.getAircraft(), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}