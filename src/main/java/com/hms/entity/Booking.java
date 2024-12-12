package com.hms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {
    // Add booking attributes here (e.g., room_id, guest_id, check_in_date, check_out_date)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "check_in_date", nullable = false)
    private LocalDate check_in_date;

    @Column(name = "check_out_date", nullable = false)
    private LocalDate check_out_date;

    @Column(name = "guest_name", nullable = false)
    private String guest_name;

    @Column(name = "no_of_guest", nullable = false)
    private Integer no_of_guest;



}
