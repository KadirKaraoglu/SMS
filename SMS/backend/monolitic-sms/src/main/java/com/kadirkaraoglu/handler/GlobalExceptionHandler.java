package com.kadirkaraoglu.handler;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.kadirkaraoglu.exception.BaseException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private List<String> addError(List<String> list , String string){
		list.add(string);
		return list;
	}
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
	 @ExceptionHandler(value = MethodArgumentNotValidException.class)
	    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request)
	            throws UnknownHostException {
	        
	        // Tüm validasyon hatalarını alıp tek bir mesajda birleştirebiliriz
	        List<String> errors = new ArrayList<>();
	        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
	            if (error instanceof FieldError) {
	                FieldError fieldError = (FieldError) error;
	                errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
	            } else {
	                errors.add(error.getDefaultMessage());
	            }
	        }
	        
	        // Hata mesajlarını virgülle ayırarak tek bir string yapın
	        String errorMessage = String.join("; ", errors);
	        if (errorMessage.isEmpty()) {
	            errorMessage = "Validation failed.";
	        }
	        
	        // createApiError metodunu kullanarak ApiError objesi oluşturun
	        return ResponseEntity.badRequest().body(createApiError(errorMessage, request));
	    }

	    // YENİ HALİ: HttpMessageNotReadableException'ı ApiError formatında döndürüyoruz
	    @ExceptionHandler(HttpMessageNotReadableException.class)
	    public ResponseEntity<ApiError> handleInvalidFormat(HttpMessageNotReadableException ex, WebRequest request)
	            throws UnknownHostException {
	        // Hata mesajınızı ErrorMessage enum'unuzdan veya sabit bir stringden alabilirsiniz
	        // Örneğin: String message = MessageType.INVALID_DATE_FORMAT.getMessage(); (eğer böyle bir enum'unuz varsa)
	        String message = "Invalid data format. Please check the date format (e.g., MM/dd/yyyy).";
	        
	        // createApiError metodunu kullanarak ApiError objesi oluşturun
	        return ResponseEntity.badRequest().body(createApiError(message, request));
	    }
}