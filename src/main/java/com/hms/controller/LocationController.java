package com.hms.controller;

import com.hms.entity.Location;
import com.hms.payloads.LocationDto;
import com.hms.repository.LocationRepository;
import com.hms.service.LocationSerivce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    private LocationRepository locationRepository;
    private LocationSerivce locationSerivce;

    public LocationController(LocationRepository locationRepository, LocationSerivce locationSerivce) {
        this.locationRepository = locationRepository;
        this.locationSerivce = locationSerivce;
    }

    @PostMapping("/addLocation")
    public ResponseEntity<?> addLocation(
            @RequestBody LocationDto locationDto) {
        try {
            LocationDto locationDto1 = locationSerivce.addLocation(locationDto);
            return new ResponseEntity<>(locationDto1, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteLocation(
            @RequestParam String name) {

        return null;
    }

}
