package com.esu.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esu.reservation.model.User;

public interface UserRespository extends JpaRepository<User, String>{

}
