package com.kadirkaraoglu.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.dto.DtoCourse;
import com.kadirkaraoglu.dto.DtoGrade;
import com.kadirkaraoglu.dto.DtoGradeUI;
import com.kadirkaraoglu.dto.DtoTeacher;
@RestController
public interface ITeacherController {
	

	public void updateGrade(DtoGradeUI dtoGradeUI) throws Exception;
	public ResponseEntity<DtoGrade> saveGrade (DtoGradeUI gradeUI) throws Exception;
	 public ResponseEntity<DtoTeacher> getCurrent();
	 public ResponseEntity<Set<DtoCourse> > listourseOfTecaher(); 
	 public ResponseEntity<List<DtoTeacher>> listAllTeacher();
	
	
}
