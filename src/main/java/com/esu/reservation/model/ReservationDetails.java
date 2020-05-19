package com.esu.reservation.model;

import javax.persistence.Entity;

@Entity
public class ReservationDetails {

	private Long id;
	private String date;
	private String time;
	private Integer maxGuests;
	
	public ReservationDetails() {}
	
	public ReservationDetails(Long id, String date, String time, Integer maxGuests) {
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.maxGuests = maxGuests;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
