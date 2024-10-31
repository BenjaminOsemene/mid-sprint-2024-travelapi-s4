package com.keyin.travelapi.controller;

import com.keyin.travelapi.model.Aircraft;
import com.keyin.travelapi.model.Airport;
import com.keyin.travelapi.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public ResponseEntity<List<Airport>> getAllAirports(){
        List<Airport> airports = airportService.getAllAirports();
        return new ResponseEntity<>(airports, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
        return airportService.getAirportById(id)
                .map(airport -> new ResponseEntity<>(airport, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Airport> createAirport(@RequestBody Airport airport) {
        Airport createdAirport = airportService.saveAirport(airport);
        return new ResponseEntity<>(createdAirport, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirport(@PathVariable Long id, @RequestBody Airport airportDetails) {
        return airportService.getAirportById(id)
                .map(existingAirport -> {
                    existingAirport.setName(airportDetails.getName());
                    existingAirport.setCode(airportDetails.getCode());
                    existingAirport.setCity(airportDetails.getCity());
                    Airport updatedAirport = airportService.saveAirport(existingAirport);
                    return new ResponseEntity<>(updatedAirport, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirport(@PathVariable Long id) {
        return airportService.getAirportById(id)
                .map(airport -> {
                    airportService.deleteAirport(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/aircraft")
    public ResponseEntity<List<Aircraft>> getAirportAircraft(@PathVariable Long id) {
        return airportService.getAirportById(id)
                .map(airport -> new ResponseEntity<>(airport.getAircraft(), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}