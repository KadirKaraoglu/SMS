package com.kadirkaraoglu.controller.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/course")
public class CourseConroller implements ICourseController {
	private final ICourseService courseService;

	@Autowired
	public CourseConroller(ICourseService courseService) {

		this.courseService = courseService;
	}

	@Override
	@GetMapping("/list-all-course")
	public ResponseEntity<List<DtoCourse>> listAllCourse() throws Exception {

		return ResponseEntity.ok(courseService.listAllCourse());
	}

	@Override
	@PutMapping("/add-student-course")
	public ResponseEntity<Boolean> addStudentTheCourses(@RequestBody @Valid StudentCourseRequest studentCourseRequest)
			throws Exception {
		return ResponseEntity.ok(courseService.addStudentTheCourses(
				studentCourseRequest));
	}	

	@Override
	@PutMapping("/out-student-course")
	public ResponseEntity<Boolean> outStudentFromCourses(@RequestBody @Valid StudentCourseRequest studentCourseRequest)
			throws Exception {

		return ResponseEntity.ok(courseService.outStudentFromCourses(studentCourseRequest
				));
	}

	@Override
	@GetMapping("/list-all-course-of-student")
	public ResponseEntity<Set<DtoCourse>> listAllCoursesOfStudent()
			throws Exception {
		return ResponseEntity.ok(courseService.listAllCoursesOfStudent());
	}

	@Override
	@GetMapping("/list-all-student-on-course/{id}")
	public ResponseEntity<Set<DtoStudent>> listAllStudentOnCourse(@PathVariable(name = "id") Long courseId)
			throws Exception {
		return ResponseEntity.ok(courseService.listAllStudentOnCourse(courseId));
	}

	@Override
	@GetMapping("/find-course-by-id/{id}")
	public ResponseEntity<DtoCourse> findById(@PathVariable(name = "id") Long courseId) throws Exception {

		return ResponseEntity.ok(courseService.findById(courseId));
	}

}
