package com.esu.reservation.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

import com.esu.reservation.exceptions.MissingUserIdException;
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
	
	
	
	@GetMapping()
	public CollectionModel<Availability> allAvailableTimes(@RequestParam String user_key) {
		
		if(user_key.isEmpty()) 
			throw new MissingUserIdException("User_id Not Present");
		
		List<Availability> times = repository.findAll();
		Link link = linkTo(this.getClass()).withSelfRel();
	
		CollectionModel<Availability> model = CollectionModel.of(times);
		
		for (Availability time: times) {
			if(time.getAvailable()) {
				Link selfLink = linkTo(methodOn(this.getClass()).showOne(time.getId(), user_key)).withSelfRel();
				time.add(selfLink);
			}
		}
		model.add(link);
		return model;
	
	}
	
	@GetMapping("/{id}")
	public EntityModel<Availability> showOne(@PathVariable Long id, @RequestParam String user_key) {
		
		if(user_key.isEmpty()) 
			throw new MissingUserIdException("User_id Not Present");
		
		Availability time = repository.findById(id).get();
		EntityModel<Availability> model = EntityModel.of(time);

		if(!time.getAvailable()) {
			return model;
		}
		Link link = linkTo(methodOn(this.getClass()).allAvailableTimes(user_key)).withRel("all-available");
		Link reserve = linkTo(methodOn(this.getClass()).reserve(id,user_key)).withRel("reserve");
		model = EntityModel.of(time);
		model.add( reserve);
		model.add(link);
		return model;
	}
	

	@GetMapping("/{id}/book")
	public EntityModel<Reservation> reserve(@PathVariable Long id, @RequestParam String user_key) {
		
		if(user_key.isEmpty()) 
			throw new MissingUserIdException("User_id Not Present");
		
		User user = userRepo.findById(user_key).get();

		
		Availability time = repository.findById(id).get();
		time.setAvailable(false);
		repository.save(time);
		
		Reservation reserve = new Reservation();
		reserve.setId(id);
		reserve.setDate(time.getDate());
		reserve.setMaxGuests(time.getMaxGuests());
		reserve.setTime(time.getTime());
		reserve.setUser(user);
		
		Link link = linkTo(methodOn(this.getClass()).allAvailableTimes(user_key)).withRel("all-available");
		EntityModel<Reservation> model = EntityModel.of(reservationRepo.save(reserve));
		model.add(link);
		
		return model;

		
	}

}
