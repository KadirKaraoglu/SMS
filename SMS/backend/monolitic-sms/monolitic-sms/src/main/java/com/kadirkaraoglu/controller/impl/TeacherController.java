package com.kadirkaraoglu.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.controller.ITeacherController;
import com.kadirkaraoglu.dto.DtoTeacher;
import com.kadirkaraoglu.service.ITeacherService;
@RestController
@RequestMapping("/api/teacher")
public class TeacherController implements ITeacherController {
	private final ITeacherService teacherService;

	@Autowired
	public TeacherController(ITeacherService teacherService) {
		super();
		this.teacherService = teacherService;
	}


	@Override
	@GetMapping("/find-teacher-by-id/{id}")
	public ResponseEntity<DtoTeacher> findTeacherById(@PathVariable(name = "id") Long id) throws Exception {
		// TODO Auto-generated method stub
		return  ResponseEntity.ok(teacherService.findTeacherById(id));
	}


}
