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
import org.springframework.web.bind.annotation.RestController;

import com.esu.reservation.model.AvailableTimes;
import com.esu.reservation.repository.AvailableTimesRepository;

@RestController
@RequestMapping("/reservations/available")
public class AvailableReservationController {
	

	@Autowired
	private AvailableTimesRepository repository;
	//get available reservations (get homepage) 
	@GetMapping("")
	public CollectionModel<AvailableTimes> allAvailableTimes() {
		
		List<AvailableTimes> times = repository.findAll();
		Link link = linkTo(this.getClass()).withSelfRel();
	
		CollectionModel<AvailableTimes> model = CollectionModel.of(times);
		
		for (AvailableTimes time: times) {
			Link selfLink = linkTo(methodOn(this.getClass()).showOne(time.getId())).withSelfRel();
			time.add(selfLink);
		}
		model.add(link);
		return model;
	
	}
	
	//check specific time (get one)
	@GetMapping("/{id}")
	public EntityModel<AvailableTimes> showOne(@PathVariable Long id) {
		
		AvailableTimes time = repository.findById(id).get();
		Link link = linkTo(methodOn(this.getClass()).allAvailableTimes()).withRel("all-available");
		
		EntityModel<AvailableTimes> model = EntityModel.of(time);
		model.add(link);
		
		return model;
	}
	
	//reserve
	
	
	//cancel reservation (delete)
	
	

}
