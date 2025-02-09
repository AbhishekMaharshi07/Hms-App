package com.hms.service;

import com.hms.entity.City;
import com.hms.payloads.CityDto;
import com.hms.repository.CityRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private ModelMapper modelMapper;
    private CityRepository cityRepository;


    public CityService(ModelMapper modelMapper, CityRepository cityRepository) {
        this.modelMapper = modelMapper;
        this.cityRepository = cityRepository;

    }

    public CityDto addCity(CityDto cityDto) {
        City city= maptoEntity(cityDto);
        cityRepository.save(city);
        CityDto dto = mapToDto(city);
        return dto;
    }

    public List<City> getAllCity() {
        List<City> all = cityRepository.findAll();
        return all;
    }

    @Transactional
    public void deleteCityById(long id) {
        cityRepository.deleteById(id);
    }

    public boolean updateCityById(long cityId, CityDto cityDto) {
        Optional<City> city = cityRepository.findById(cityId);
        if(city.isPresent()){
            City city1 = city.get();
            city1.setName(cityDto.getName());
            cityRepository.save(city1);
            System.out.println("Hello world");
            return true;
        }else {
            return false;
        }
    }

    // map city dto to city entity
    City maptoEntity(CityDto cityDto){
        City map = modelMapper.map(cityDto, City.class);
        return map;
    }

    CityDto mapToDto(City city){
        CityDto map = modelMapper.map(city, CityDto.class);
        return map;
    }


}
