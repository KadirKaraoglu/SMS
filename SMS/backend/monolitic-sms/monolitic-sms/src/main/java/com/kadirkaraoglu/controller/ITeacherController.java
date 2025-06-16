package com.kadirkaraoglu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.dto.DtoTeacher;
@RestController
public interface ITeacherController {
	
	public ResponseEntity<DtoTeacher > findTeacherById(Long id) throws Exception;
	
	
}
