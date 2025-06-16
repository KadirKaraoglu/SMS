package com.kadirkaraoglu.handler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Exception<E> { 
	
	private String hostName;
	
	private String path;
	
	private java.util.Date createTime;
	
	private E message;
}
