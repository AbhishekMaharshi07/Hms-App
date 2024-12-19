package com.hms.repository;

import com.hms.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    public Optional<Property> findByName(String name);

    @Query(
            "select p from Property p JOIN p.location l JOIN p.city c JOIN p.country co where c.name=:name or co.name=:name or l.name=:name")
    List<Property> searchHotels(
            @Param("name") String name);
}