package com.hms.payloads;

import com.hms.entity.City;
import com.hms.entity.Country;
import com.hms.entity.Location;
import lombok.Data;

@Data
public class PropertyDto {

    private Long id;

    private String name;

    private Integer no_of_guest;

    private Integer no_of_bedrooms;

    private Integer no_of_bathrooms;

    private Integer no_of_beds;

    private Location location;
    private Long location_id;

    private Country country;
    private Long country_id;

    private City city;
    private Long city_id;



}

