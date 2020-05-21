package com.esu.reservation.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esu.reservation.exceptions.MissingApiIdException;
import com.esu.reservation.model.Availability;
import com.esu.reservation.model.Reservation;
import com.esu.reservation.repository.AvailabilityRepository;
import com.esu.reservation.repository.ReservationRepository;
import com.esu.reservation.repository.UserRespository;

@RestController
@RequestMapping("/my-restaurant/reservation")
public class ReservationController {

	@Autowired
	private ReservationRepository repository;
	
	@Autowired
	private UserRespository  userRepo;
	
	@Autowired
	private AvailabilityRepository availabilityRepo;

	//get user reservation by api-id
	@GetMapping("")
	public CollectionModel<Reservation> showAllReservations(@RequestParam String userId) {
		
		if(userId.isEmpty() || !userRepo.findById(userId).isPresent())
			throw new MissingApiIdException("Api-id Not Found");
		
		List<Reservation> reservations = repository.findByUserId(userId);
		
		for(Reservation reservation: reservations) {
			Link selfLink = linkTo(methodOn(this.getClass())
								.cancelReservation(reservation.getId()))
								.withRel("cancel");
			reservation.add(selfLink);
		}
		
		Link link = linkTo(this.getClass()).withSelfRel();
		
		CollectionModel<Reservation> model = CollectionModel.of(reservations);
		model.add(link);
		
		return model;
	}
	
	//get one reservation 
	@GetMapping("/{id}")
	public EntityModel<Reservation> showOneReservation(@PathVariable Long id ,@RequestParam String userId) {
		Reservation reservation = repository.findById(id).get();
		
		Link link = linkTo(methodOn(this.getClass()).showAllReservations(userId)).withRel("all-reservations");
		
		EntityModel<Reservation> model = EntityModel.of(reservation);
		model.add(link);
		
		return model;
	}
	
	//cancel a reservation
	@GetMapping("/cancel/{id}")
	public ResponseEntity<Object> cancelReservation(@PathVariable Long id) {
		repository.deleteById(id);
		Availability available = availabilityRepo.findById(id).get();
		available.setAvailable(true);
		availabilityRepo.save(available);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	
	
}
