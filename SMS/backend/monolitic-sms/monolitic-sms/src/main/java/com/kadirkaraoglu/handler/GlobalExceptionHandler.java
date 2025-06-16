package com.kadirkaraoglu.handler;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.kadirkaraoglu.exception.BaseException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(value = { BaseException.class })
	public ResponseEntity<ApiError> handlebaseException(BaseException baseException, WebRequest request)
			throws UnknownHostException {
		return ResponseEntity.badRequest().body(createApiError(baseException.getMessage(), request));

	}

	private String getHostname() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}

	public <E> ApiError<E> createApiError(E message, WebRequest request) throws UnknownHostException {
		ApiError<E> apiError = new ApiError<E>();
		apiError.setStates(HttpStatus.BAD_REQUEST.value());
		Exception<E> exception = new Exception<E>();
		exception.setCreateTime(new java.util.Date());
		exception.setHostName(getHostname());
		exception.setPath(request.getDescription(false).substring(4));
		exception.setMessage(message);
		apiError.setException(exception);
		return apiError;
	}

}