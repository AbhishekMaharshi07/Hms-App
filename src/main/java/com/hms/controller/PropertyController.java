package com.hms.controller;

import com.hms.entity.Property;
import com.hms.payloads.PropertyDto;
import com.hms.repository.PropertyRepository;
import com.hms.service.PropertyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {
    private PropertyService propertyService;
    private PropertyRepository propertyRepository;

    public PropertyController(PropertyService propertyService, PropertyRepository propertyRepository) {
        this.propertyService = propertyService;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/add_property")
    public ResponseEntity<?> addProperty(
            @RequestBody PropertyDto propertyDto,
            @RequestParam long country_id,
            @RequestParam long city_id
    ) {
        ResponseEntity<?> property1 = propertyService.addProperty(propertyDto, country_id, city_id);
        return new ResponseEntity<>(property1, HttpStatus.CREATED);

    }

    @GetMapping("/getAllProperty")
    public ResponseEntity<?> getAllProperty() {
        List<Property> allProperty = propertyService.getAllProperty();
        if (allProperty == null) {
            return new ResponseEntity<>("No any property found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(allProperty, HttpStatus.FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePropertyById(@PathVariable("id") long property_id) {
        Optional<Property> byId = propertyRepository.findById(property_id);
        if(byId.isPresent()){
            propertyService.deletePropertyById(property_id);
            return new ResponseEntity<>("Property deleted successfully", HttpStatus.OK);
        }else{
            return  new ResponseEntity<>("Property not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getPropertyById/{id}")
    public ResponseEntity<?> getPropertyById(@PathVariable("id") long property_id){
        Optional<Property> byId = propertyService.getPropertyById(property_id);
        if(byId.isPresent()){
            Property property = byId.get();
            return new ResponseEntity<>(property, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Property not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("updatePropertyById/{id}")
    public ResponseEntity<?> updatePropertyById(@PathVariable("id") long property_id,
                                                @RequestBody PropertyDto propertyDto){
        boolean status = propertyService.updatePropertyById(property_id, propertyDto);
        if(status){
            return new ResponseEntity<>("Property updated successfully", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Property not found", HttpStatus.NOT_FOUND);
        }

    }

}
