package com.kadirkaraoglu.handler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ApiError<E> {
	private Integer states;
	
	private Exception<E> exception;

}
