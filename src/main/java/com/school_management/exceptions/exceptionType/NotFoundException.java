package com.school_management.exceptions.exceptionType;

import com.school_management.exceptions.GlobalException;

public class NotFoundException extends GlobalException {
	public NotFoundException(String message) {
		super(message);
	}
}
