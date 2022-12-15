package com.sathler.workshopmongo.resources;

import java.time.Instant;
import java.util.List;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sathler.workshopmongo.resources.exception.StandardError;
import com.sathler.workshopmongo.services.exception.ObjectAlreadyExists;
import com.sathler.workshopmongo.services.exception.ObjectNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;

		StandardError err = new StandardError(
				Instant.now(),
				status.value(),
				status.getReasonPhrase(),
				e.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ObjectAlreadyExists.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectAlreadyExists e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.CONFLICT;

		StandardError err = new StandardError(
				Instant.now(),
				status.value(),
				status.getReasonPhrase(),
				e.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> badRequest(MethodArgumentNotValidException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		List<String> errors = e.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.toList();

		StandardError err = new StandardError(
				Instant.now(),
				status.value(),
				status.getReasonPhrase(),
				errors.toString(),
				request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<StandardError> methodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;

		StandardError err = new StandardError(
				Instant.now(),
				status.value(),
				status.getReasonPhrase(),
				e.getMessage(),
				request.getRequestURI());

		return ResponseEntity.status(status).body(err);
	}
	
}
