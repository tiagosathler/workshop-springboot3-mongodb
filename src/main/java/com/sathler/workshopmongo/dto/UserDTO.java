package com.sathler.workshopmongo.dto;

import java.io.Serializable;

import com.sathler.workshopmongo.domain.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	
	@NotNull(message = "Name cannot be null")
	@Size(min = 3, max = 30, message = "Name must be between 3 to 30 characters")
	private String name;
	
	@NotNull(message = "Email cannot be null")
	@Email(message = "Email invalid")
	private String email;

	public UserDTO() {
	}

	public UserDTO(User user) {
		id = user.getId();
		name = user.getName();
		email = user.getEmail();
	}

	public User userFromDTO() {
		return new User(getId(), getName(), getEmail());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", name=" + name + ", email=" + email + "]";
	}
}
