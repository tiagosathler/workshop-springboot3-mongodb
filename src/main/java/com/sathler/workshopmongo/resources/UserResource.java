package com.sathler.workshopmongo.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		UserDTO userDTO = new UserDTO(service.findById(id));

		return ResponseEntity.ok().body(userDTO);
	}

	@PostMapping
	public ResponseEntity<UserDTO> insert(@RequestBody UserDTO userDTO) {
		User user = service.insert(userDTO.userFromDTO());

		userDTO.setId(user.getId());

		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(user.getId())
				.toUri();

		return ResponseEntity.created(uri).body(userDTO);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);

		return ResponseEntity.noContent().build();
	}
}
