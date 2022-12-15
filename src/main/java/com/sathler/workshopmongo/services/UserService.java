package com.sathler.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sathler.workshopmongo.domain.Post;
import com.sathler.workshopmongo.domain.User;
import com.sathler.workshopmongo.repositories.UserRepository;
import com.sathler.workshopmongo.services.exception.ObjectAlreadyExists;
import com.sathler.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(String id) {
		Optional<User> user = repository.findById(id);

		if (user.isEmpty()) {
			throw new ObjectNotFoundException("User id '" + id + "' not found!");
		}

		return user.get();
	}

	public User insert(User user) {
		User foundUserByEmail = repository.findByEmailIgnoreCase(user.getEmail());

		if (foundUserByEmail != null) {
			throw new ObjectAlreadyExists("User email '" + user.getEmail() + "' already exists");
		}
		return repository.insert(user);
	}

	public void deleteById(String id) {
		findById(id);
		repository.deleteById(id);
	}

	public User updateById(String id, User user) {
		User foundUserById = findById(id);

		User foundUserByEmail = repository.findByEmailIgnoreCase(user.getEmail());

		if (foundUserByEmail != null && !foundUserById.equals(foundUserByEmail)) {
			throw new ObjectAlreadyExists("User email '" + user.getEmail() + "' already exists in another user");
		}

		updateData(foundUserById, user);
		return repository.save(foundUserById);
	}

	public User addPostToUser(Post post, User user) {
		user.getPosts().add(post);
		return repository.save(user);
	}

	private void updateData(User toUpdateUser, User user) {
		toUpdateUser.setName(user.getName());
		toUpdateUser.setEmail(user.getEmail());
	}
}
