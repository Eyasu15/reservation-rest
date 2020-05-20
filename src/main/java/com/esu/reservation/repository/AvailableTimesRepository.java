package com.esu.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esu.reservation.model.AvailableTimes;


public interface AvailableTimesRepository extends JpaRepository<AvailableTimes, Long>{

}
