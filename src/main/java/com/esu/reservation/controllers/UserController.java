package com.esu.reservation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.esu.reservation.model.User;
import com.esu.reservation.repository.UserRespository;

@Controller
@RequestMapping("/my-restaurant/users")
public class UserController {
	
	@Autowired
	private UserRespository  userRepository;

	
	@GetMapping("/show")
	public String show(@RequestParam String user_key, Model model) {
		User user = userRepository.findById(user_key).get();
		
		model.addAttribute("user", user);
		System.out.println(user_key);
		return "show";
	}
	
	
	@GetMapping("/register")
	public ModelAndView register(@ModelAttribute User user) {
		
		ModelAndView model = new ModelAndView("register");
		model.addObject("user", user);
		
		return model;
	}
	

	@PostMapping("/save")
	public String save(@ModelAttribute User user) {
		
		User savedUser = userRepository.save(user);
		
		
		return "redirect:/my-restaurant/users/show?user_key="+savedUser.getId();
	}
	
	
	
	//Send email 
	
}
