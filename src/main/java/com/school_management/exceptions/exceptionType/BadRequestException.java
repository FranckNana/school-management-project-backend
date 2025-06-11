package com.school_management.exceptions.exceptionType;

import com.school_management.exceptions.GlobalException;

public class BadRequestException extends com.school_management.exceptions.GlobalException {
	public BadRequestException(String message) {
		super(message);
	}
}
