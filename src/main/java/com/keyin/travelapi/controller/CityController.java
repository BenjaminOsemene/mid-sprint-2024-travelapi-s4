//package com.keyin.travelapi.controller;
//
//import com.keyin.travelapi.model.City;
//import com.keyin.travelapi.model.Airport;
//import com.keyin.travelapi.service.CityService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/cities")
//public class CityController {
//
//    private final CityService cityService;
//
//    @Autowired
//    public CityController(CityService cityService) {
//        this.cityService = cityService;
//    }
//
//    @GetMapping
//    public List<City> getAllCities() {
//        return cityService.getAllCities();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<City> getCityById(@PathVariable Long id) {
//        return cityService.getCityById(id)
//                .map(city -> new ResponseEntity<>(city, HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @PostMapping
//    public ResponseEntity<City> createCity(@RequestBody City city) {
//        City createdCity = cityService.saveCity(city);
//        return new ResponseEntity<>(createdCity, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody City cityDetails) {
//        return cityService.getCityById(id)
//                .map(existingCity -> {
//                    existingCity.setName(cityDetails.getName());
//                    existingCity.setState(cityDetails.getState());
//                    existingCity.setPopulation(cityDetails.getPopulation());
//                    City updatedCity = cityService.saveCity(existingCity);
//                    return new ResponseEntity<>(updatedCity, HttpStatus.OK);
//                })
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
//        return cityService.getCityById(id)
//                .map(city -> {
//                    cityService.deleteCity(id);
//                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//                })
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @GetMapping("/{id}/airports")
//    public ResponseEntity<List<Airport>> getCityAirports(@PathVariable Long id) {
//        return cityService.getCityById(id)
//                .map(city -> new ResponseEntity<>(city.getAirports(), HttpStatus.OK))
//                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//}



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
