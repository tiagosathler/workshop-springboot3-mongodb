package com.sathler.workshopmongo.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.sathler.workshopmongo.domain.Post;

public interface PostRepository extends MongoRepository<Post, String> {
	static final String FULL_SEARCH_QUERY = """
			{ $and: [
				{ 'date': { $gte: ?1 } },
				{ 'date': { $lte: ?2 } },
				{ $or: [
					{ 'title': { $regex: ?0, $options: 'i' }},
					{ 'body': { $regex: ?0, $options: 'i' }},
					{ 'comments.text': { $regex: ?0, $options: 'i' }},
				] } ],
			}""";

	List<Post> findByTitleContainingIgnoreCase(String text);

	@Query("{'title': { $regex: ?0, $options: 'i' }}")
	List<Post> searchTitle(String text);

	@Query(FULL_SEARCH_QUERY)
	List<Post> fullSearch(String text, Date minDate, Date maxDate);
}
