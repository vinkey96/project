package com.example.projetspring.service;


import com.example.projetspring.model.User;
import com.example.projetspring.web.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
