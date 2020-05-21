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

import com.esu.reservation.exceptions.MissingRequestParamException;
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
	

	@GetMapping("")
	public CollectionModel<Reservation> showAllReservations (@RequestParam String user_key){
	
		
		if(user_key==null || !userRepo.findById(user_key).isPresent())
			throw new MissingRequestParamException("User_id Not Present");
		
		List<Reservation> reservations = repository.findByUserId(user_key);

		for(Reservation reservation: reservations) {
			Link selfLink = linkTo(methodOn(this.getClass())
								.cancelReservation(reservation.getId()))
								.withRel("cancel");
			reservation.add(selfLink);
		}
				
		CollectionModel<Reservation> model = CollectionModel.of(reservations);
		
		return model;
	}
	
	@GetMapping("/{id}")
	public EntityModel<Reservation> showOneReservation(@PathVariable Long id ,@RequestParam String user_key) {
		Reservation reservation = repository.findById(id).get();
		
		Link link = linkTo(methodOn(this.getClass())
					.showAllReservations(user_key))
					.withRel("all-reservations");
		
		EntityModel<Reservation> model = EntityModel.of(reservation);
		model.add(link);
		
		return model;
	}
	
	@GetMapping("/cancel/{id}")
	public ResponseEntity<Object> cancelReservation(@PathVariable Long id) {
		repository.deleteById(id);
		Availability available = availabilityRepo.findById(id).get();
		available.setAvailable(true);
		availabilityRepo.save(available);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	
	
}
