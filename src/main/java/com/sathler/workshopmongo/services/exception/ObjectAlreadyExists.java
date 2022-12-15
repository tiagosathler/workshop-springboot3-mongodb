package com.sathler.workshopmongo.services.exception;

public class ObjectAlreadyExists extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjectAlreadyExists(String msg) {
		super(msg);
	}
}
