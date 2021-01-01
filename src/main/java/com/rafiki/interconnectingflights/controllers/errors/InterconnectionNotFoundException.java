package com.rafiki.interconnectingflights.controllers.errors;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InterconnectionNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InterconnectionNotFoundException() {
		super("No Interconnections found for the selected filters");
	}

}
