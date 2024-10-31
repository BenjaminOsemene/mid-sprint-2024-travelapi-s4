package com.keyin.travelapi.repository;

import com.keyin.travelapi.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    List<Aircraft> findByTypeAndAirlineName(String type, String airlineName);
}