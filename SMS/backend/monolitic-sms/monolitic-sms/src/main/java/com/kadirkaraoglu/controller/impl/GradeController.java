package com.kadirkaraoglu.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	@PreAuthorize("hasRole(ADMIN)")
	@GetMapping("/list-all-grade-of-student/{id}")
	public ResponseEntity<List<DtoGrade>> listAllGradeOfStudent(@PathVariable(name = "id") Long studentId) throws Exception {
		return ResponseEntity.ok(gradeService.listAllGradeOfStudent(studentId));
	}


	@Override
	@PostMapping("/update-grade")
	public void updateGrade(@RequestBody DtoGradeUI dtoGradeUI) throws Exception {
		
		gradeService.updateGrade(dtoGradeUI);
		
	}

	@Override
	@PutMapping("/save-grade")
	public ResponseEntity<DtoGrade> saveGrade(@RequestBody DtoGradeUI gradeUI) throws Exception {
		return ResponseEntity.ok(gradeService.saveGrade(gradeUI));
	}

}
