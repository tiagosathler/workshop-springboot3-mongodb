package com.sathler.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.sathler.workshopmongo.domain.Post;
import com.sathler.workshopmongo.domain.User;
import com.sathler.workshopmongo.dto.AuthorDTO;
import com.sathler.workshopmongo.dto.CommentDTO;
import com.sathler.workshopmongo.repositories.PostRepository;
import com.sathler.workshopmongo.repositories.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

		userRepository.deleteAll();
		postRepository.deleteAll();

		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");

		userRepository.saveAll(Arrays.asList(maria, alex, bob));

		AuthorDTO mariaDTO = new AuthorDTO(maria);
		AuthorDTO alexDTO = new AuthorDTO(alex);
		AuthorDTO bobDTO = new AuthorDTO(bob);

		Post post1 = new Post(
				null,
				sdf.parse("21/03/2018"),
				"Partiu viagem",
				"Vou viajar para São Paulo. Abraços!",
				mariaDTO);

		Post post2 = new Post(
				null,
				sdf.parse("23/03/2018"),
				"Bom dia", "Arcordei feliz hoje!",
				mariaDTO);

		CommentDTO comment1 = new CommentDTO(
				"Boa Viagem mano!",
				sdf.parse("21/03/2018"),
				alexDTO);

		CommentDTO comment2 = new CommentDTO(
				"Aproveite!",
				sdf.parse("22/03/2018"),
				bobDTO);

		CommentDTO comment3 = new CommentDTO(
				"Tenha um ótimo dia!",
				sdf.parse("23/03/2018"),
				alexDTO);

		post1.getComments().addAll(Arrays.asList(comment1, comment2));
		post2.getComments().add(comment3);

		postRepository.saveAll(Arrays.asList(post1, post2));

		maria.getPosts().addAll(Arrays.asList(post1, post2));

		userRepository.save(maria);
	}

}
