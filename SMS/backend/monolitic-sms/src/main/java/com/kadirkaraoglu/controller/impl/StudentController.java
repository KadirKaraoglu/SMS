package com.kadirkaraoglu.controller.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.controller.IStudentController;
import com.kadirkaraoglu.dto.DtoFaculty;
import com.kadirkaraoglu.dto.DtoStudent;
import com.kadirkaraoglu.entities.Student;
import com.kadirkaraoglu.service.IStudentService;

@RestController
@RequestMapping("/api/student")
public class StudentController implements IStudentController {
	private final IStudentService studentService;

	@Autowired
	public StudentController(IStudentService studentService) {
		this.studentService = studentService;
	}


	@GetMapping("/me")
	public ResponseEntity<DtoStudent> getCurrent() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Student student = studentService.findStudentByUsername(username);
		DtoStudent dtoStudent = new DtoStudent();
		BeanUtils.copyProperties(student, dtoStudent);


		dtoStudent.setFacultyName(student.getFaculty().getFacultyname());
		return ResponseEntity.ok(dtoStudent);
	}

}
