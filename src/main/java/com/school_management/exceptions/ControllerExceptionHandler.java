
package com.school_management.exceptions;

import com.school_management.exceptions.exceptionType.BadRequestException;
import com.school_management.exceptions.exceptionType.NotFoundException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler{
	
	ExceptionResponse getError(String message, String uri) {
		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(message);
		error.setRequestedURI(uri);
		return error;
	}
	
	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	public @ResponseBody ExceptionResponse notFoundExceptionHandler(NotFoundException ex, HttpServletRequest req) {
		return this.getError(ex.getMessage(), req.getRequestURI());
	}

	
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public @ResponseBody ExceptionResponse badRequestExceptionHandler(BadRequestException ex, HttpServletRequest req) {
		return this.getError(ex.getMessage(), req.getRequestURI());
	}
}
