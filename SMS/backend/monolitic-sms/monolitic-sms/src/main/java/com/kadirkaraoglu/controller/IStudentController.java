package com.kadirkaraoglu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.dto.ChangePasswordRequest;
import com.kadirkaraoglu.dto.DtoStudent;
@RestController
public interface IStudentController {
	public ResponseEntity< DtoStudent> findStudentById(Long id)throws Exception;
	
	public boolean changePassword(ChangePasswordRequest changePasswordRequest)throws Exception;
}
