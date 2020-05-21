package com.esu.reservation.exceptions;

import java.time.LocalTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalTime.now(),ex.getMessage(),request.getDescription(false));
	
		return new ResponseEntity<Object>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(MissingUserIdException.class)
	public final ResponseEntity<Object> handleMissingApiIdException(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(LocalTime.now(),ex.getMessage(),request.getDescription(false));
	
		return new ResponseEntity<Object>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    	ErrorDetails errorDetails = new ErrorDetails(LocalTime.now(),"Missing User Key",request.getDescription(false));

	    return new ResponseEntity<Object>(errorDetails, HttpStatus.NOT_FOUND);
	}
}