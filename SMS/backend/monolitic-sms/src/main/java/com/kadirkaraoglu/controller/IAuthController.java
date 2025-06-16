package com.kadirkaraoglu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.dto.AuthResponse;
import com.kadirkaraoglu.dto.ChangePasswordRequest;
import com.kadirkaraoglu.dto.LoginRequest;

import jakarta.validation.Valid;

@RestController
public interface IAuthController {

	public  ResponseEntity<AuthResponse>  loginStudent(LoginRequest loginRequest) throws Exception;
	public  ResponseEntity<AuthResponse> loginTeacher(LoginRequest loginRequest) throws Exception;
	public boolean changePassword(ChangePasswordRequest changePasswordRequest)throws Exception;
	ResponseEntity<AuthResponse> loginAdmin(@Valid LoginRequest loginRequest) throws Exception;

}
