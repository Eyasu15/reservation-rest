package com.esu.reservation.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esu.reservation.model.Availability;
import com.esu.reservation.model.Reservation;
import com.esu.reservation.model.User;
import com.esu.reservation.repository.AvailabilityRepository;
import com.esu.reservation.repository.ReservationRepository;
import com.esu.reservation.repository.UserRespository;

@RestController
@RequestMapping("/my-restaurant/availability")
public class AvailableReservationController {
	

	@Autowired
	private AvailabilityRepository repository;

	@Autowired
	private ReservationRepository reservationRepo;
	
	@Autowired
	private UserRespository  userRepo;
	
	//get available reservations (get homepage) 
	@GetMapping("")
	public CollectionModel<Availability> allAvailableTimes() {
		
		List<Availability> times = repository.findAll();
		Link link = linkTo(this.getClass()).withSelfRel();
	
		CollectionModel<Availability> model = CollectionModel.of(times);
		
		for (Availability time: times) {
			if(time.getAvailable()) {
				Link selfLink = linkTo(methodOn(this.getClass()).showOne(time.getId())).withSelfRel();
				time.add(selfLink);
			}
		}
		model.add(link);
		return model;
	
	}
	
	//check specific time (get one)
	@GetMapping("/{id}")
	public EntityModel<Availability> showOne(@PathVariable Long id) {
		
		Availability time = repository.findById(id).get();
		EntityModel<Availability> model = EntityModel.of(time);

		if(!time.getAvailable()) {
			return model;
		}
		Link link = linkTo(methodOn(this.getClass()).allAvailableTimes()).withRel("all-available");
		Link reserve = linkTo(methodOn(this.getClass()).reserve(id, "esu")).withRel("reserve");
		model = EntityModel.of(time);
		model.add( reserve);
		model.add(link);
		return model;
	}
	
	//reserve
	@GetMapping("/{id}/book")
	public EntityModel<Reservation> reserve(@PathVariable Long id, @RequestParam String userId) {
		
		User user = userRepo.findById(userId).get();

		
		Availability time = repository.findById(id).get();
		time.setAvailable(false);
		repository.save(time);
		
		Reservation reserve = new Reservation();
		reserve.setId(id);
		reserve.setDate(time.getDate());
		reserve.setMaxGuests(time.getMaxGuests());
		reserve.setTime(time.getTime());
		reserve.setUser(user);
		
		Link link = linkTo(methodOn(this.getClass()).allAvailableTimes()).withRel("all-available");
		EntityModel<Reservation> model = EntityModel.of(reservationRepo.save(reserve));
		model.add(link);
		
		return model;

		
	}
	
	
	//cancel reservation (delete)
	
	

}
