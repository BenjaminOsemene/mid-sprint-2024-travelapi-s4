package com.keyin.travelapi.controller;

import com.keyin.travelapi.model.Aircraft;
import com.keyin.travelapi.model.Passenger;
import com.keyin.travelapi.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
public class AircraftController {

    private final AircraftService aircraftService;

    @Autowired
    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @GetMapping
    public ResponseEntity<List<Aircraft>> getAllAircraft(){
        List<Aircraft> aircraft = aircraftService.getAllAircraft();
        return new ResponseEntity<>(aircraft, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aircraft> getAircraftById(@PathVariable Long id) {
        return aircraftService.getAircraftById(id)
                .map(aircraft -> new ResponseEntity<>(aircraft, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Aircraft> createAircraft(@RequestBody Aircraft aircraft) {
        Aircraft createdAircraft = aircraftService.saveAircraft(aircraft);
        return new ResponseEntity<>(createdAircraft, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aircraft> updateAircraft(@PathVariable Long id, @RequestBody Aircraft aircraftDetails) {
        return aircraftService.getAircraftById(id)
                .map(existingAircraft -> {
                    existingAircraft.setType(aircraftDetails.getType());
                    existingAircraft.setAirlineName(aircraftDetails.getAirlineName());
                    existingAircraft.setNumberOfPassengers(aircraftDetails.getNumberOfPassengers());
                    Aircraft updatedAircraft = aircraftService.saveAircraft(existingAircraft);
                    return new ResponseEntity<>(updatedAircraft, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(@PathVariable Long id) {
        return aircraftService.getAircraftById(id)
                .map(aircraft -> {
                    aircraftService.deleteAircraft(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/passengers")
    public ResponseEntity<List<Passenger>> getAircraftPassengers(@PathVariable Long id) {
        return aircraftService.getAircraftById(id)
                .map(aircraft -> new ResponseEntity<>(aircraft.getPassengers(), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}