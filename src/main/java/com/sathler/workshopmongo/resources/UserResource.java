package com.sathler.workshopmongo.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sathler.workshopmongo.domain.User;
import com.sathler.workshopmongo.dto.UserDTO;
import com.sathler.workshopmongo.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> users = service.findAll();
		
		List<UserDTO> usersDTO = users.stream().map(UserDTO::new).toList();

		return ResponseEntity.ok().body(usersDTO);
	}
}
