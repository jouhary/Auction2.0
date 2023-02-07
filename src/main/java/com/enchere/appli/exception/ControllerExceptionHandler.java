package com.enchere.appli.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(AuthentificationFailedException.class)
	public ResponseEntity<ErrorMessage> AuthentificationFailedException(AuthentificationFailedException ex, WebRequest request){
		ErrorMessage error = new ErrorMessage(
				HttpStatus.UNAUTHORIZED.value(), 
				new Date(), 
				ex.getMessage(), 
				request.getDescription(false));
		
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(com.enchere.appli.exception.SessionLostException.class)
	public ResponseEntity<ErrorMessage> SessionLostException(SessionLostException ex, WebRequest request){
		
		ErrorMessage error = new ErrorMessage(
				HttpStatus.UNAUTHORIZED.value(), 
				new Date(), 
				ex.getMessage(), 
				request.getDescription(false));
		
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> ResourceNotFoundException(ResourceNotFoundException ex, WebRequest request){
		
		ErrorMessage error = new ErrorMessage(
				HttpStatus.NOT_FOUND.value(), 
				new Date(), 
				ex.getMessage(), 
				request.getDescription(false));
		return new ResponseEntity<ErrorMessage>(error, HttpStatus.NOT_FOUND);
	}
}












