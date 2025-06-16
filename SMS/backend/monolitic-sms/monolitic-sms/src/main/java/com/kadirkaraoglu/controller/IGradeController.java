package com.kadirkaraoglu.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.dto.DtoGrade;
import com.kadirkaraoglu.dto.DtoGradeUI;
@RestController
public interface IGradeController {
	public ResponseEntity<List<DtoGrade>>  listAllGradeOfStudent(Long studentId)throws Exception;
	public void updateGrade(DtoGradeUI dtoGradeUI) throws Exception;
	public ResponseEntity<DtoGrade> saveGrade (DtoGradeUI gradeUI) throws Exception;
}
