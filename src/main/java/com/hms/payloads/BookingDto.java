package com.hms.payloads;

import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDto {

    private Long id;

    private LocalDate check_in_date;

    private LocalDate check_out_date;

    private String guest_name;

    private Integer no_of_guest;

}
