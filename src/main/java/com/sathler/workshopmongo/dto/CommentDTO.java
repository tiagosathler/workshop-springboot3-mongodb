package com.sathler.workshopmongo.dto;

import java.io.Serializable;
import java.util.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

public class CommentDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "Text cannot be null")
	@Size(min = 3, max = 100, message = "Text must be between 3 to 30 characters")
	private String text;
	
	@NotNull(message = "Date cannot be null")
	@PastOrPresent(message = "Date must be an instant, date or time in the past or in the present")
	private Date date;

	private AuthorDTO author;

	public CommentDTO() {
	}

	public CommentDTO(String text, Date date, AuthorDTO author) {
		super();
		this.text = text;
		this.date = date;
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}

}
