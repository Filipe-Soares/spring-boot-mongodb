package com.spring.api.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.api.domain.User;
import com.spring.api.dto.UserDto;
import com.spring.api.services.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity<List<UserDto>>  findAll(){
		List<User> list = userService.findAll();
		List<UserDto> listDto = list.stream().map(x-> new UserDto(x)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);
	}

	@GetMapping(value="/{id}")
	public ResponseEntity<UserDto>  findById(@PathVariable("id") String id){
		User user = userService.findById(id);
		
		return ResponseEntity.ok().body(new UserDto(user));
	}

	@PostMapping
	public ResponseEntity<Void>  insert(@RequestBody UserDto userDto){
		User user = userService.fromDto(userDto);
		user = userService.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri()
;		return ResponseEntity.created(uri).build();
	}
}
