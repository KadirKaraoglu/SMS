package com.kadirkaraoglu.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.dto.DtoCourse;
import com.kadirkaraoglu.dto.DtoCourseUI;
import com.kadirkaraoglu.dto.DtoStudent;
import com.kadirkaraoglu.dto.DtoStudentUI;
import com.kadirkaraoglu.dto.DtoTeacher;
import com.kadirkaraoglu.dto.DtoTeacherUI;
import com.kadirkaraoglu.entities.Faculty;
@RestController
public interface IAdminController {
	public void deleteCourse(Long CourseId)throws Exception;
	public ResponseEntity<DtoCourse>  saveCourse (DtoCourseUI courseUI);
	public void deleteStudent(Long id) throws Exception;
	public ResponseEntity<DtoStudent>  updateStudent(DtoStudentUI dtoStudentUI)throws Exception ; 
	public ResponseEntity<DtoStudent>  saveStudent(DtoStudentUI dtoStudentUI)throws Exception;
	public void deleteTeacher(Long id)throws Exception;
	public ResponseEntity<DtoTeacher>  updateTeacher(DtoTeacherUI dtoTeacherUI)throws Exception; 
	public ResponseEntity<DtoTeacher>  saveTeacher(DtoTeacherUI dtoTeacherUI) throws Exception;
	public ResponseEntity<List<DtoTeacher>>  listAllTeacher();
	public ResponseEntity<List<DtoStudent>>  listAllStudent ();
	public ResponseEntity<List<Faculty>> listFaculties();


}
