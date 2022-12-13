package com.sathler.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sathler.workshopmongo.domain.User;
import com.sathler.workshopmongo.repositories.UserRepository;
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
			throw new ObjectNotFoundException("Object id '" + id + "' not found!");
		}

		return user.get();
	}

	public User insert(User user) {
		return repository.insert(user);
	}

	public void deleteById(String id) {
		findById(id);
		repository.deleteById(id);
	}

	public User updateById(String id, User user) {
		User foundUser = findById(id);
		updateData(foundUser, user);
		return repository.save(foundUser);
	}

	private void updateData(User toUpdateUser, User user) {
		toUpdateUser.setName(user.getName());
		toUpdateUser.setEmail(user.getEmail());
	}
}
