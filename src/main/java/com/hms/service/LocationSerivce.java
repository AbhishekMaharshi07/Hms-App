package com.hms.service;

import com.hms.entity.City;
import com.hms.entity.Location;
import com.hms.exception.RecordAlreadyExistsException;
import com.hms.payloads.CityDto;
import com.hms.payloads.LocationDto;
import com.hms.repository.LocationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LocationSerivce {

    private LocationRepository locationRepository;
    private ModelMapper modelMapper;

    public LocationSerivce(LocationRepository locationRepository, ModelMapper modelMapper) {
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;
    }

    public LocationDto addLocation(LocationDto locationDto) {
        Optional<Location> byName = locationRepository.findByName(locationDto.getName());
        if(byName.isPresent()){
            throw new RuntimeException("Location already exists");
        }
        Location location = maptoEntity(locationDto);
        locationRepository.save(location);
        LocationDto dto = mapToDto(location);
        return dto;
    }



    Location maptoEntity(LocationDto locationDto){
        Location map = modelMapper.map(locationDto, Location.class);
        return map;
    }

    LocationDto mapToDto(Location location){
        LocationDto map = modelMapper.map(location, LocationDto.class);
        return map;
    }
}
