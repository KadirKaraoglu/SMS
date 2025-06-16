package com.kadirkaraoglu.exception;

import lombok.Getter;

@Getter

public enum MessageType {
	
	NO_RECORD_EXIST("1001","no record found"),
	NO_RECORD_EXIST_COURSE("1002","The Course could not find !"),
	NO_RECORD_EXIST_STUDENT("1003","The Student could not find !"),
	COURSE_IS_FULL("1004","THE quota of the course is full"),
	YOU_DONT_TAKE_THIS_COURSE("1005","You do not take this course"),
	NO_RECORD_EXIST_FACULTY("1006","The Faculty could not find !"),
	INVALID_TCKN("1007","The TCKN you entered is invalid"),
	OLD_PASSWORD_IS_WRONG("1008","Old password is wrong"),
	NO_RECORD_EXIST_TEACHER("1003","The teacher could not find !");
	private String code;
	private String message;
	MessageType(String code , String message)
	{
	this.code= code;
	this.message= message;
	}
	
	
	
	
}