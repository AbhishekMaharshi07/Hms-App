package com.hms.service;

import com.hms.entity.Country;
import com.hms.payloads.CountryDto;
import com.hms.repository.CountryRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    private CountryRepository countryRepository;
    private ModelMapper modelMapper;

    public CountryService(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    public CountryDto addCountry(CountryDto countryDto) {
        Country country = mapToEntity(countryDto);
        countryRepository.save(country);
        CountryDto dto = mapToDto(country);
        return dto;
    }

    public List<Country> getAllCountry() {
        List<Country> all = countryRepository.findAll();
        return all;
        // return List.of();
    }

    @Transactional
    public void deleteCountryById(long id) {

        countryRepository.deleteById(id);
    }

    public boolean updateCountryById(long countryId, CountryDto countryDto) {
        Optional<Country> country = countryRepository.findById(countryId);
        if(country.isPresent()){
            Country country1 = country.get();
            country1.setName(countryDto.getName());
            countryRepository.save(country1);
            return true;
        }else{
            return false;
        }
    }


    // map city dto to city entity
    Country mapToEntity(CountryDto countryDto){
        Country map = modelMapper.map(countryDto, Country.class);
        return map;
    }

    CountryDto mapToDto(Country country){
        CountryDto map = modelMapper.map(country, CountryDto.class);
        return map;
    }


}
