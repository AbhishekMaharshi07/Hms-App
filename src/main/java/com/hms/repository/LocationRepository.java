package com.hms.repository;

import com.hms.entity.City;
import com.hms.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
  public Optional<Location> findByName(String name);
}