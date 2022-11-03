package com.spring.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.api.domain.User;
import com.spring.api.dto.UserDto;
import com.spring.api.repository.UserRepository;
import com.spring.api.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public User findById(String id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new ObjectNotFoundException("No records found");
		}
		return user.get();
	}
	
	public User insert(User obj) {
		return userRepository.insert(obj);
	}
	
	public User fromDto(UserDto objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}
}
