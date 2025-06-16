package com.kadirkaraoglu.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.controller.IStudentController;
import com.kadirkaraoglu.dto.ChangePasswordRequest;
import com.kadirkaraoglu.dto.DtoStudent;
import com.kadirkaraoglu.service.IStudentService;
@RestController
@RequestMapping("/api/student")
public class StudentController implements IStudentController {
	private final IStudentService studentService; 
	@Autowired
	public StudentController(IStudentService studentService) {
		this.studentService = studentService;
	}


	@Override
	@GetMapping("/find-student-by-id/{id}")
	public ResponseEntity<DtoStudent> findStudentById(@PathVariable(name = "id" ) Long id) throws Exception {
		return ResponseEntity.ok(studentService.findStudentById(id));
	}

	
	@Override
	@PostMapping("/change-password")
	public boolean changePassword(ChangePasswordRequest changePasswordRequest) throws Exception {
		return studentService.changePassword(changePasswordRequest);
	}

}
