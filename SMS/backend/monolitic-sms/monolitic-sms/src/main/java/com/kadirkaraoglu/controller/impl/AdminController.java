package com.kadirkaraoglu.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.controller.IAdminController;
import com.kadirkaraoglu.dto.DtoCourse;
import com.kadirkaraoglu.dto.DtoCourseUI;
import com.kadirkaraoglu.dto.DtoStudent;
import com.kadirkaraoglu.dto.DtoStudentUI;
import com.kadirkaraoglu.dto.DtoTeacher;
import com.kadirkaraoglu.dto.DtoTeacherUI;
import com.kadirkaraoglu.service.ICourseService;
import com.kadirkaraoglu.service.IStudentService;
import com.kadirkaraoglu.service.ITeacherService;


@RestController
@RequestMapping("/api/admin")
public class AdminController implements IAdminController{
	
	private final ICourseService courseService;
	private final ITeacherService teacherService;
	private final IStudentService studentService;

	@Autowired
	public AdminController(ICourseService courseService, ITeacherService teacherService,
			IStudentService studentService) {
	
		this.courseService = courseService;
		this.teacherService = teacherService;
		this.studentService = studentService;
	}

	@Override
	@DeleteMapping("/delete-course/{id}")
	public void deleteCourse(@PathVariable(name = "id")  Long CourseId) throws Exception {
		courseService.deleteCourse(CourseId);
		
	}

	@Override
	@PutMapping("/save-course")
	public ResponseEntity<DtoCourse> saveCourse(@RequestBody DtoCourseUI courseUI) {
		return  new ResponseEntity<>(courseService.saveCourse(courseUI),HttpStatus.CREATED);
	}
	@Override
	@DeleteMapping("/delete-student/{id}")
	public void deleteStudent(@PathVariable(name = "id" ) Long id) throws Exception {
		studentService.deleteStudent(id);
		
	}

	@Override
	@PostMapping("/update-student")
	public ResponseEntity<DtoStudent> updateStudent(@RequestBody DtoStudentUI dtoStudentUI) throws Exception {
		// TODO Auto-generated method stub
		return ResponseEntity.ok(studentService.updateStudent(dtoStudentUI));
	}

	@Override
	@PutMapping("/save-student")
	public ResponseEntity<DtoStudent> saveStudent(@RequestBody DtoStudentUI dtoStudentUI) throws Exception {
		
		return  new ResponseEntity<>(studentService.saveStudent(dtoStudentUI),HttpStatus.CREATED);
	}
	@Override
	@GetMapping("/list-all-teacher")
	public ResponseEntity<List<DtoTeacher>> listAllTeacher() {
		
		return ResponseEntity.ok(teacherService.listAllTeacher());
	}

	@Override
	@DeleteMapping("/delete-teacher/{id}")
	public void deleteTeacher(@PathVariable(name = "id" ) Long id) throws Exception {
		teacherService.deleteTeacher(id);
		
	}

	@Override
	@PostMapping("/update-teacher")
	public ResponseEntity<DtoTeacher> updateTeacher(@RequestBody DtoTeacherUI dtoTeacherUI) throws Exception {

		return  ResponseEntity.ok(teacherService.updateTeacher(dtoTeacherUI));
	}

	@Override
	@PutMapping("/save-teacher")
	public ResponseEntity<DtoTeacher> saveTeacher(@RequestBody DtoTeacherUI dtoTeacherUI) throws Exception {
		
		return  new ResponseEntity<>(teacherService.saveTeacher(dtoTeacherUI),HttpStatus.CREATED);
	}


}
