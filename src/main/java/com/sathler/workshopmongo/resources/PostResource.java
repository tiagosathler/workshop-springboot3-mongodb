package com.sathler.workshopmongo.resources;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sathler.workshopmongo.domain.Post;
import com.sathler.workshopmongo.domain.User;
import com.sathler.workshopmongo.dto.CommentDTO;
import com.sathler.workshopmongo.dto.PostDTO;
import com.sathler.workshopmongo.resources.util.URL;
import com.sathler.workshopmongo.services.PostService;
import com.sathler.workshopmongo.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/posts")
public class PostResource {

	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Post> findById(@PathVariable String id) {
		Post post = postService.findById(id);
		return ResponseEntity.ok().body(post);
	}

	@GetMapping(value = "/titlesearch")
	public ResponseEntity<List<Post>> findByTitle(@RequestParam(value = "text", defaultValue = "") String text) {
		text = URL.decodeParam(text);

		List<Post> posts = postService.findByTitle(text);
		return ResponseEntity.ok().body(posts);
	}

	@GetMapping(value = "/fullsearch")
	public ResponseEntity<List<Post>> fullSearch(
			@RequestParam(value = "text", defaultValue = "") String text,
			@RequestParam(value = "minDate", defaultValue = "") String minDateStr,
			@RequestParam(value = "maxDate", defaultValue = "") String maxDateStr) {

		text = URL.decodeParam(text);
		Date minDate = URL.convertDate(minDateStr, new Date(0L));
		Date maxDate = URL.convertDate(maxDateStr, new Date());

		List<Post> posts = postService.fullSearch(text, minDate, maxDate);
		return ResponseEntity.ok().body(posts);
	}
	
	@PostMapping(value = "/user/{id}")
	public ResponseEntity<PostDTO> insertPost(
			@PathVariable String id,
			@Valid @RequestBody PostDTO postDTO) {
		User foundUser = userService.findById(id);

		Post newPost = postService.insertPost(foundUser, postDTO.postFromDTO());

		userService.addPostToUser(newPost, foundUser);

		PostDTO resultPost = new PostDTO(newPost);

		URI uri = getPostURI(newPost);

		return ResponseEntity.created(uri).body(resultPost);
	}
	
	@PostMapping(value = "/{postId}/user/{userId}")
	public ResponseEntity<Post> insertComment(
			@PathVariable String postId,
			@PathVariable String userId,
			@Valid @RequestBody CommentDTO commentDTO) {
		Post foundPost = postService.findById(postId);
		User foundUser = userService.findById(userId);

		Post updatedPost = postService.insertComment(foundUser, foundPost, commentDTO);

		URI uri = getPostURI(updatedPost);

		return ResponseEntity.created(uri).body(updatedPost);
	}

	private static URI getPostURI(Post post) {
		return UriComponentsBuilder
				.fromPath("/posts")
				.path("/{id}")
				.buildAndExpand(post.getId())
				.toUri();
	}
}
