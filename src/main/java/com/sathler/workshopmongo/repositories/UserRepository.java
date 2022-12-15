package com.sathler.workshopmongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sathler.workshopmongo.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findByEmailIgnoreCase(String email);
}
