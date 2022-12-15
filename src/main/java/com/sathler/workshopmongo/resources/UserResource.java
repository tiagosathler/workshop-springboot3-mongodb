package com.sathler.workshopmongo.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sathler.workshopmongo.domain.Post;
import com.sathler.workshopmongo.domain.User;
import com.sathler.workshopmongo.dto.UserDTO;
import com.sathler.workshopmongo.services.UserService;

import jakarta.validation.Valid;

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

	@GetMapping(value = "/{id}/posts")
	public ResponseEntity<List<Post>> findPostsByUserId(@PathVariable String id) {
		User user = service.findById(id);

		return ResponseEntity.ok().body(user.getPosts());
	}

	@PostMapping
	public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserDTO userDTO) {
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
	public ResponseEntity<Void> deleteById(@PathVariable String id) {
		service.deleteById(id);

		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> updateById(@PathVariable String id, @Valid @RequestBody UserDTO userDTO) {
		User user = service.updateById(id, userDTO.userFromDTO());

		return ResponseEntity.ok().body(new UserDTO(user));
	}
}
