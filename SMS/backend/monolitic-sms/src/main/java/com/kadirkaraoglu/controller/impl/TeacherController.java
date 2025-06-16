package com.kadirkaraoglu.controller.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.controller.ITeacherController;
import com.kadirkaraoglu.dto.DtoCourse;
import com.kadirkaraoglu.dto.DtoGrade;
import com.kadirkaraoglu.dto.DtoGradeUI;
import com.kadirkaraoglu.dto.DtoTeacher;
import com.kadirkaraoglu.entities.Teacher;
import com.kadirkaraoglu.service.IGradeService;
import com.kadirkaraoglu.service.ITeacherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController implements ITeacherController {
	private final ITeacherService teacherService;
	private final IGradeService gradeService;

	@Autowired
	public TeacherController(ITeacherService teacherService, IGradeService gradeServic) {
		this.gradeService = gradeServic;

		this.teacherService = teacherService;
	}



	@Override
	@PostMapping("/update-grade")
	public void updateGrade(@RequestBody @Valid DtoGradeUI dtoGradeUI) throws Exception {

		gradeService.updateGrade(dtoGradeUI);

	}

	@Override
	@PutMapping("/save-grade")
	public ResponseEntity<DtoGrade> saveGrade(@RequestBody @Valid DtoGradeUI gradeUI) throws Exception {
		return ResponseEntity.ok(gradeService.saveGrade(gradeUI));
	}

	@GetMapping("/me")
	public ResponseEntity<DtoTeacher> getCurrent() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Teacher teacher = teacherService.findTeacherByUsername(username);
		DtoTeacher dtoTeacher = new DtoTeacher();
		BeanUtils.copyProperties(teacher, dtoTeacher);
		return ResponseEntity.ok(dtoTeacher);
	}



	@Override
	@GetMapping("/list-course-of-teacher")
	public ResponseEntity<Set<DtoCourse>> listourseOfTecaher() {
		
		return (ResponseEntity.ok(teacherService.listourseOfTecaher())) ;
	}



	@Override
	@GetMapping("/list-all-teacher")
	public ResponseEntity<List<DtoTeacher>> listAllTeacher() {
		return ResponseEntity.ok(teacherService.listAllTeacher()) ;
	}

}
