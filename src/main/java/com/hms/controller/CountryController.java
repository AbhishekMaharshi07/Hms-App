package com.hms.controller;

import com.hms.entity.City;
import com.hms.entity.Country;
import com.hms.payloads.CityDto;
import com.hms.payloads.CountryDto;
import com.hms.payloads.PropertyDto;
import com.hms.repository.CountryRepository;
import com.hms.service.CountryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {

    private CountryService countryService;
    private ModelMapper modelMapper;
    private CountryRepository countryRepository;

    public CountryController(CountryService countryService, ModelMapper modelMapper, CountryRepository countryRepository) {
        this.countryService = countryService;
        this.modelMapper = modelMapper;
        this.countryRepository = countryRepository;
    }

    //ADD COUNTRY
    @PostMapping("/addCountry")
    public ResponseEntity<?> addCountry(@RequestBody CountryDto countryDto){
        Optional<Country> byName = countryRepository.findByName(countryDto.getName());
        if(byName.isPresent()){
            return new ResponseEntity<>("Country already exits", HttpStatus.NOT_ACCEPTABLE);
        }else{
            countryService.addCountry(countryDto);
            return new ResponseEntity<>("Country added successfully", HttpStatus.CREATED);
        }
    }

    @GetMapping("/getAllCountry")
    public ResponseEntity<?> getAllCountry(){
        List<Country> allCountry = countryService.getAllCountry();
        if(allCountry == null){
            return new ResponseEntity<>("No any country found", HttpStatus.NOT_FOUND);
        }else {
            return  new ResponseEntity<>(allCountry,HttpStatus.FOUND);
        }
    }

    @DeleteMapping("/deleteCountryById/{id}")
    public ResponseEntity<?> deleteCountryById(@PathVariable long id){
        Optional<Country> byId = countryRepository.findById(id);
        if(byId.isPresent()){
            countryService.deleteCountryById(id);
            return  new ResponseEntity<>("Country deleted successfully",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("id not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getCountryById/{id}")
    public ResponseEntity<?> getCountryById(@PathVariable("id") long country_id){
        Optional<Country> byId = countryRepository.findById(country_id);
        if(byId.isPresent()){
            Country country = byId.get();
            return  new ResponseEntity<>(country,HttpStatus.OK);
        }else{
            return  new ResponseEntity<>("Country not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateCountryById/{id}")
    public ResponseEntity<?> updateCountryById(@PathVariable("id") long country_id,
                                            @RequestBody CountryDto countryDto){
        boolean status = countryService.updateCountryById(country_id, countryDto);
        if(status){
            return new ResponseEntity<>("Country name updated",HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>("Country not found",HttpStatus.NOT_FOUND);
        }
    }
}
