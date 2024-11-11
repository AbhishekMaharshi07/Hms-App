package com.hms.service;

import com.hms.entity.City;
import com.hms.entity.Country;
import com.hms.entity.Property;
import com.hms.payloads.PropertyDto;
import com.hms.repository.CityRepository;
import com.hms.repository.CountryRepository;
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

    public PropertyService(PropertyRepository propertyRepository, CountryRepository countryRepository, CityRepository cityRepository, ModelMapper modelMapper) {
        this.propertyRepository = propertyRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<?> addProperty(PropertyDto propertyDto, long countryId, long cityId) {
        Optional<Property> byName = propertyRepository.findByName(propertyDto.getName());
        if (byName.isPresent()) {
            return new ResponseEntity<>("Property already exists", HttpStatus.NOT_ACCEPTABLE);
        }
        Property property = mapToEntity(propertyDto);
        Property saved = propertyRepository.save(property);

        PropertyDto dto = mapToDto(saved);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    Property mapToEntity(PropertyDto propertyDto) {
        Property mapProperty= modelMapper.map(propertyDto, Property.class);
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
                ()-> new RuntimeException("Property not found")
        );

        Country country = countryRepository.findById(propertyDto.getCountry_id()).orElseThrow(
                ()-> new RuntimeException("Country not found")
        );

        City city =  cityRepository.findById(propertyDto.getCity_id()).orElseThrow(
                ()-> new RuntimeException("city not found")
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

