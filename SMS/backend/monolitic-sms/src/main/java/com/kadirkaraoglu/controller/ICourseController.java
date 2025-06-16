package com.kadirkaraoglu.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.dto.DtoCourse;
import com.kadirkaraoglu.dto.DtoStudent;
import com.kadirkaraoglu.dto.StudentCourseRequest;

@RestController
public interface ICourseController {
	
	public ResponseEntity<List<DtoCourse>>  listAllCourse()throws Exception;
	public ResponseEntity<Boolean> addStudentTheCourses( StudentCourseRequest studentCourseRequest)throws Exception; 
	public ResponseEntity<Boolean> outStudentFromCourses( StudentCourseRequest studentCourseRequest)throws Exception;
	public ResponseEntity<Set<DtoCourse>>  listAllCoursesOfStudent()throws Exception;
	public ResponseEntity<Set<DtoStudent>>  listAllStudentOnCourse(Long courseId)throws Exception;
	public ResponseEntity<DtoCourse>  findById(Long courseId) throws Exception;
	

}
