package com.esu.reservation.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private User user;
	private ReservationDetails details;
	
	public Reservation() {};
	
	public Reservation(Long id, User user, ReservationDetails details) {
		super();
		this.id = id;
		this.user = user;
		this.details = details;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public ReservationDetails getDetails() {
		return details;
	}


	public void setDetails(ReservationDetails details) {
		this.details = details;
	}
	
	
}
