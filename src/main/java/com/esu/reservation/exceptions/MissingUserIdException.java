package com.esu.reservation.exceptions;

public class MissingUserIdException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MissingUserIdException(String message) {
        super(message);
    }
	
}
