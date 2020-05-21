package com.esu.reservation.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.hateoas.RepresentationModel;

@Entity
public class Reservation extends RepresentationModel<Reservation> {

	@Id
	private Long reservationId;
	@ManyToOne
	private User user;
	private String date;
	private String time;
	private Integer maxGuests;

	public Reservation() {
	}

	public Reservation(Long id, User user) {
		this.reservationId = id;
		this.user = user;
	}

	public Long getId() {
		return reservationId;
	}

	public void setId(Long id) {
		this.reservationId = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getMaxGuests() {
		return maxGuests;
	}

	public void setMaxGuests(Integer maxGuests) {
		this.maxGuests = maxGuests;
	}

}
