package com.hms.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "check_in_date", nullable = false)
    private LocalDate check_in_date;

    @Column(name = "check_out_date", nullable = false)
    private LocalDate check_out_date;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

}
