package com.hms.payloads;

import lombok.Data;

@Data
public class PropertyDto {

    private Long id;

    private String name;

    private Integer no_of_guest;

    private Integer no_of_bedrooms;

    private Integer no_of_bathrooms;

    private Integer no_of_beds;

    private String country_name;
    private Long country_id;

    private String city_name;
    private Long city_id;



}

