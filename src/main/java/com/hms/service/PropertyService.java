package com.hms.service;

import com.hms.entity.City;
import com.hms.entity.Country;
import com.hms.entity.Location;
import com.hms.entity.Property;
import com.hms.payloads.PropertyDto;
import com.hms.payloads.UserDto;
import com.hms.repository.CityRepository;
import com.hms.repository.CountryRepository;
import com.hms.repository.LocationRepository;
import com.hms.repository.PropertyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {
    private PropertyRepository propertyRepository;
    private CountryRepository countryRepository;
    private CityRepository cityRepository;
    private ModelMapper modelMapper;
    private final LocationRepository locationRepository;

    public PropertyService(PropertyRepository propertyRepository, CountryRepository countryRepository, CityRepository cityRepository, ModelMapper modelMapper,
                           LocationRepository locationRepository) {
        this.propertyRepository = propertyRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
        this.locationRepository = locationRepository;
    }

    public PropertyDto addProperty(
            PropertyDto propertyDto, long locationId, long cityId, long countryId) {

        Optional<Property> byName = propertyRepository.findByName(propertyDto.getName());
        if (byName.isPresent()) {
            throw new IllegalArgumentException("property already exists");
        }
        Location location = locationRepository.findById(locationId).orElseThrow(
                () -> new RuntimeException("Services is not available for your location"));
        City city = cityRepository.findById(cityId).orElseThrow(
                () -> new RuntimeException("Services is not available for your city"));
        Country country = countryRepository.findById(countryId).orElseThrow(
                () -> new RuntimeException("Services is not available for your country"));


        Property property = mapToEntity(propertyDto);
        property.setCountry(country);
        property.setCity(city);
        property.setLocation(location);

        Property save = propertyRepository.save(property);
        PropertyDto dto = mapToDto(save);
        return dto;
    }


    Property mapToEntity(PropertyDto propertyDto) {
        Property mapProperty = modelMapper.map(propertyDto, Property.class);
        return mapProperty;
    }

    PropertyDto mapToDto(Property property) {
        PropertyDto mapPropertyDto = modelMapper.map(property, PropertyDto.class);
        return mapPropertyDto;
    }

    public List<Property> getAllProperty() {
        List<Property> all = propertyRepository.findAll();
        return all;
    }

    public void deletePropertyById(long property_id) {
        propertyRepository.deleteById(property_id);
    }

    public Optional<Property> getPropertyById(long propertyId) {
        Optional<Property> byId = propertyRepository.findById(propertyId);
        return byId;
    }

    public boolean updatePropertyById(long property_id, PropertyDto propertyDto) {
        Property property = propertyRepository.findById(property_id).orElseThrow(
                () -> new RuntimeException("Property not found")
        );

        Country country = countryRepository.findById(propertyDto.getCountry_id()).orElseThrow(
                () -> new RuntimeException("Country not found")
        );

        City city = cityRepository.findById(propertyDto.getCity_id()).orElseThrow(
                () -> new RuntimeException("city not found")
        );

        property.setName(propertyDto.getName());
        property.setNo_of_bedrooms(propertyDto.getNo_of_bedrooms());
        property.setNo_of_beds(propertyDto.getNo_of_beds());
        property.setNo_of_bathrooms(propertyDto.getNo_of_bathrooms());

        property.setCountry(country);
        property.setCity(city);

        propertyRepository.save(property);
        return true;
    }
}

