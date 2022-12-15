package com.sathler.workshopmongo.dto;

import java.io.Serializable;
import java.util.Date;

import com.sathler.workshopmongo.domain.Post;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public class PostDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	@NotNull(message = "Date cannot be null")
	@PastOrPresent(message = "Date must be an instant, date or time in the past or in the present")
	private Date date;

	@NotNull(message = "Title cannot be null")
	@Size(min = 3, max = 30, message = "Title must be between 3 to 30 characters")
	private String title;

	@NotNull(message = "Text cannot be null")
	@Size(min = 3, max = 100, message = "Body text must be between 3 to 100 characters")
	private String body;

	private AuthorDTO author;
	
	public PostDTO() {
	}

	public PostDTO(Post post) {
		super();
		this.id = post.getId();
		this.date = post.getDate();
		this.title = post.getTitle();
		this.body = post.getBody();
		this.author = post.getAuthor();
	}

	public Post postFromDTO() {
		return new Post(getId(), getDate(), getTitle(), getBody(), getAuthor());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "PostDTO [id=" + id + ", date=" + date + ", title=" + title + ", body=" + body + ", author=" + author
				+ "]";
	}
}
