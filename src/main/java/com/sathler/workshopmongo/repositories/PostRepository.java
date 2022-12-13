package com.sathler.workshopmongo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sathler.workshopmongo.domain.Post;

public interface PostRepository extends MongoRepository<Post, String> {

}
