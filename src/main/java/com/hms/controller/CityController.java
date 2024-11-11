package com.hms.controller;

import com.hms.entity.City;
import com.hms.payloads.CityDto;
import com.hms.repository.CityRepository;
import com.hms.service.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/city")
public class CityController {

    private CityRepository cityRepository;
    private CityService cityService;

    public CityController(CityRepository cityRepository, CityService cityService) {
        this.cityRepository = cityRepository;
        this.cityService = cityService;
    }


    @PostMapping("/addCity")
    public ResponseEntity<?> addCity(@RequestBody CityDto cityDto){
        Optional<City> byName = cityRepository.findByName(cityDto.getName());
        if(byName.isPresent()){
            return new ResponseEntity<>("City already exits", HttpStatus.NOT_ACCEPTABLE);
        }else{
            cityService.addCity(cityDto);
            return new ResponseEntity<>("City added successfully", HttpStatus.CREATED);
        }
    }

    @GetMapping("/getAllCity")
    public ResponseEntity<?> getAllCity(){
        List<City> allCity = cityService.getAllCity();
        if(allCity == null){
            return new ResponseEntity<>("No any city found", HttpStatus.NOT_FOUND);
        }else {
            return  new ResponseEntity<>(allCity,HttpStatus.FOUND);
        }
    }

    @DeleteMapping("/deleteCityById/{id}")
    public ResponseEntity<?> deleteCityById(@PathVariable long id){
        Optional<City> byId = cityRepository.findById(id);
        if(byId.isPresent()){
            cityService.deleteCityById(id);
            return  new ResponseEntity<>("City deleted successfully",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("id not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getCityById/{id}")
    public ResponseEntity<?> getCityById(@PathVariable("id") long city_id){
        Optional<City> byId = cityRepository.findById(city_id);
        if(byId.isPresent()){
            City city = byId.get();
            return  new ResponseEntity<>(city,HttpStatus.OK);
        }else{
            return  new ResponseEntity<>("City not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateCityById/{id}")
    public ResponseEntity<?> updateCityById(@PathVariable("id") long city_id,
                                            @RequestBody CityDto cityDto){
        boolean status = cityService.updateCityById(city_id, cityDto);
        if(status){
            return new ResponseEntity<>("City name updated",HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("City not found",HttpStatus.NOT_FOUND);
        }
    }
}
