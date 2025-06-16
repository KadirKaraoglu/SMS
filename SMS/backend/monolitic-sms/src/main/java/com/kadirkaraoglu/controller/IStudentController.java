package com.kadirkaraoglu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.dto.DtoStudent;
@RestController
public interface IStudentController {
	
	 public ResponseEntity<DtoStudent> getCurrent();
	
	
}
