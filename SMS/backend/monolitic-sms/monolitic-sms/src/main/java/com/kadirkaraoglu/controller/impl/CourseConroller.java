package com.kadirkaraoglu.controller.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.controller.ICourseController;
import com.kadirkaraoglu.dto.DtoCourse;
import com.kadirkaraoglu.dto.DtoStudent;
import com.kadirkaraoglu.dto.StudentCourseRequest;
import com.kadirkaraoglu.service.ICourseService;
@RestController
@RequestMapping("/api/course")
public class CourseConroller implements ICourseController{
	private final ICourseService courseService;
	@Autowired
	public CourseConroller(ICourseService courseService) {
		
		this.courseService = courseService;
	}

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/list-all-course")
	public ResponseEntity<List<DtoCourse>> listAllCourse() throws Exception {
		
		return ResponseEntity.ok(courseService.listAllCourse());
	}

	@Override
	@PutMapping("/add-student-course")
	public ResponseEntity<String> addStudentTheCourses(@RequestBody StudentCourseRequest studentCourseRequest) throws Exception {
		return ResponseEntity.ok(courseService.addStudentTheCourses(studentCourseRequest.getStudentId(), studentCourseRequest.getCourseIds()));
	}

	@Override
	@PutMapping("/out-student-course")
	public ResponseEntity<String> outStudentFromCourses(@RequestBody StudentCourseRequest studentCourseRequest) throws Exception {
		
		return ResponseEntity.ok(courseService.outStudentFromCourses(studentCourseRequest.getStudentId(), studentCourseRequest.getCourseIds()));
	}

	@Override
	@GetMapping("/list-all-course-of-student/{id}")
	public ResponseEntity<Set<DtoCourse>> listAllCoursesOfStudent (@PathVariable(name = "id") Long studentId) throws Exception {
		return ResponseEntity.ok(courseService.listAllCoursesOfStudent(studentId));
	}

	@Override
	@GetMapping("/list-all-student-on-course/{id}")
	public ResponseEntity<Set<DtoStudent>> listAllStudentOnCourse(@PathVariable(name = "id") Long courseId) throws Exception {
		return ResponseEntity.ok(courseService.listAllStudentOnCourse(courseId));
	}

	@Override
	@GetMapping("/find-course-by-id/{id}")
	public ResponseEntity<DtoCourse> findById(@PathVariable(name = "id")  Long courseId) throws Exception {
		
		return ResponseEntity.ok(courseService.findById(courseId));
	}

	



}
