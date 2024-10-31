//package com.keyin.travelapi.service;
//
//import com.keyin.travelapi.model.City;
//import com.keyin.travelapi.repository.CityRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CityService {
//    private static final Logger logger = LoggerFactory.getLogger(CityService.class);
//    private final CityRepository cityRepository;
//
//    @Autowired
//    public CityService(CityRepository cityRepository) {
//        this.cityRepository = cityRepository;
//    }
//
//    @Transactional(readOnly = true)
//    public List<City> getAllCities() {
//        logger.info("Fetching all cities with pagination");
//        return cityRepository.findAll();
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<City> getCityById(Long id) {
//        logger.info("Fetching city with id: {}", id);
//        return cityRepository.findById(id);
//    }
//
//    @Transactional
//    public City saveCity(City city) {
//        logger.info("Saving city: {}", city);
//        return cityRepository.save(city);
//    }
//
//    @Transactional
//    public void deleteCity(Long id) {
//        logger.info("Deleting city with id: {}", id);
//        cityRepository.deleteById(id);
//    }
//
//    public Object getAirportsForCity(long l) {
//        return null;
//    }
//
//    public Object addAirportToCity(long l, long l1) {
//        return null;
//    }
//
//    public Object removeAirportFromCity(long l, long l1) {
//        return null;
//    }
//}


package com.keyin.travelapi.service;

import com.keyin.travelapi.model.City;
import com.keyin.travelapi.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private static final Logger logger = LoggerFactory.getLogger(CityService.class);
    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Transactional(readOnly = true)
    public List<City> getAllCities() {
        logger.info("Fetching all cities");
        return cityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<City> getCityById(Long id) {
        logger.info("Fetching city with id: {}", id);
        return cityRepository.findById(id);
    }

    @Transactional
    public City saveCity(City city) {
        logger.info("Saving city: {}", city);
        return cityRepository.save(city);
    }

    @Transactional
    public void deleteCity(Long id) {
        logger.info("Deleting city with id: {}", id);
        cityRepository.deleteById(id);
    }
}

