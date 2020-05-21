package com.esu.reservation.exceptions;

public class MissingApiIdException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingApiIdException(String message) {
        super(message);
    }
	
}
