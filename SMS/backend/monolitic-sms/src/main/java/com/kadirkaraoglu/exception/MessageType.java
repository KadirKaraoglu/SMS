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
	NO_RECORD_EXIST_TEACHER("1009","The teacher could not find !"),
	TCKN_ALREADY_EXIST("1010","Identity no you entered is already exist"),
	PASSWORD_WRONG("1011","Password you entered is wrong"),
	TIME_OUT("1012","Your session has timed out"),
	YOU_ALREADY_TAKE_THIS_COURSE("1013","You already take this course"),
	CHANGE_PASSWORD("1014","Please Change password first login"),
	GRADE_SAVE_ERROR("1015","Grade can not change"),
	LOGIN_ERROR_STUDENT("1016","This username does not belong to the student"),
	LOGIN_ERROR_TEACHER("1016","This username does not belong to the teacher"),
	LOGIN_ERROR_ADNIN("1016","This username does not belong to the admin");
	
	
	
	private String code;
	private String message;
	MessageType(String code , String message)
	{
	this.code= code;
	this.message= message;
	}
	
	
	
	
}