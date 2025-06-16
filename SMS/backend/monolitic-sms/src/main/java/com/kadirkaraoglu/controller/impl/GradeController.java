package com.kadirkaraoglu.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.controller.IGradeController;
import com.kadirkaraoglu.dto.DtoGrade;
import com.kadirkaraoglu.dto.DtoGradeUI;
import com.kadirkaraoglu.service.IGradeService;
@RestController
@RequestMapping("/api/grade")
public class GradeController implements IGradeController {
	
	private final IGradeService gradeService;
	@Autowired
	public GradeController(IGradeService gradeService) {
		
		this.gradeService = gradeService;
	}


	@Override
	
	@GetMapping("/list-all-grade-of-student")
	public ResponseEntity<List<DtoGrade>> listAllGradeOfStudent() throws Exception {
		return ResponseEntity.ok(gradeService.listAllGradeOfStudent());
	}


	@Override
	@GetMapping("/save-grade")
	public ResponseEntity<DtoGrade> savegrade(DtoGradeUI dtoGradeUI) throws Exception {
		return ResponseEntity.ok(gradeService.saveGrade(dtoGradeUI));
	}



}
