package com.esu.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esu.reservation.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

}