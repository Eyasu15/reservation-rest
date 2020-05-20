package com.esu.reservation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esu.reservation.model.Reservation;
import com.esu.reservation.repository.ReservationRepository;
import com.esu.reservation.repository.UserRespository;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

	@Autowired
	private ReservationRepository repository;
	

	//get user reservation by api-id
	@GetMapping("/my-reservations")
	public CollectionModel<Reservation> showAllReservations() {
		List<Reservation> reservations = repository.findAll();
		
		for(Reservation reservation: reservations) {
			Link selfLink = linkTo(methodOn(this.getClass()).showOneReservation(reservation.getId())).withSelfRel();
			reservation.add(selfLink);
		}
		
		Link link = linkTo(this.getClass()).withSelfRel();
		
		CollectionModel<Reservation> model = CollectionModel.of(reservations);
		model.add(link);
		
		return model;
	}
	
	//get one reservation 
	@GetMapping("/my-reservations/{id}")
	public EntityModel<Reservation> showOneReservation(@PathVariable Long id) {
		Reservation reservation = repository.findById(id).get();
		
		Link link = linkTo(methodOn(this.getClass()).showAllReservations()).withRel("all-reservations");
		
		EntityModel<Reservation> model = EntityModel.of(reservation);
		model.add(link);
		
		return model;
	}
	//book a reservation 
	
	
	//cancel a reservation
	

	
	
}
