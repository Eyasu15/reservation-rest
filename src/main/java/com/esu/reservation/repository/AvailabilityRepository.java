package com.esu.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esu.reservation.model.Availability;


public interface AvailabilityRepository extends JpaRepository<Availability, Long>{

}
