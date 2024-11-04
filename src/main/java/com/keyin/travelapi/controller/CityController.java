package com.keyin.travelapi.controller;

import com.keyin.travelapi.model.City;
import com.keyin.travelapi.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable Long id) {
        Optional<City> city = cityService.getCityById(id);
        return city.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<City> createCity(@RequestBody City city) {
        City savedCity = cityService.saveCity(city);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody City city) {
        if (!cityService.getCityById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        city.setId(id);
        return ResponseEntity.ok(cityService.saveCity(city));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        if (!cityService.getCityById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}
