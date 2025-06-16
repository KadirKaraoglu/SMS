package com.kadirkaraoglu.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kadirkaraoglu.dto.DtoGrade;
import com.kadirkaraoglu.dto.DtoGradeUI;
import com.kadirkaraoglu.entities.Course;
import com.kadirkaraoglu.entities.Grade;
import com.kadirkaraoglu.entities.Student;
import com.kadirkaraoglu.exception.BaseException;
import com.kadirkaraoglu.exception.ErrorMessage;
import com.kadirkaraoglu.repository.ICourseRepository;
import com.kadirkaraoglu.repository.IGradeRepository;
import com.kadirkaraoglu.repository.StudentRepository;
import com.kadirkaraoglu.service.IGradeService;
@Service
public class GradeServiceImpl implements IGradeService{

	private final IGradeRepository gradeRepository;
	private final ICourseRepository courseRepository;
	private final StudentRepository studentRepository;
	@Autowired
	public GradeServiceImpl(IGradeRepository gradeRepository, ICourseRepository courseRepository,
			StudentRepository studentRepository) {
		this.gradeRepository = gradeRepository;
		this.courseRepository = courseRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	public void updateGrade(DtoGradeUI dtoGradeUI) throws Exception {
		Optional<Course> optional = courseRepository.findById(dtoGradeUI.getCourseId());
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_COURSE, dtoGradeUI.toString())) ;
		}
		Optional<Student> optional2 = studentRepository.findById(dtoGradeUI.getStudentId());
		if (optional2.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT, dtoGradeUI.toString())) ;
		}
		
		Grade grade = new Grade();
		grade.setScore(dtoGradeUI.getScore());
		gradeRepository.save(grade);
		
		DtoGrade dtoGrade = new DtoGrade();
		dtoGrade.setScore(dtoGradeUI.getScore());
	}

	@Override
	public DtoGrade saveGrade(DtoGradeUI dtoGradeUI) throws Exception {
		Optional<Course > optional = courseRepository.findById(dtoGradeUI.getCourseId());
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_COURSE, dtoGradeUI.toString())) ;
		}
		Optional<Student> optional2 = studentRepository.findById(dtoGradeUI.getStudentId());
		if (optional2.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT, dtoGradeUI.toString())) ;
		}
		Grade grade = new Grade();
	
		grade.setScore(dtoGradeUI.getScore());
		gradeRepository.save(grade);
		
		DtoGrade dtoGrade = new DtoGrade();
		dtoGrade.setScore(dtoGradeUI.getScore());
		return dtoGrade;
		
	}

	@Override
	public List<DtoGrade> listAllGradeOfStudent(Long studentId) throws Exception{
		List<DtoGrade> dtoGrades = new ArrayList<DtoGrade>();
		Optional<Student> optional = studentRepository.findById(studentId);
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT, studentId.toString())) ;
		}
		Student student = optional.get();
		Set<Grade> grades = student.getGrades();
		for (Grade grade : grades) {
			DtoGrade dtoGrade = findById(grade.getId());
		dtoGrades.add(dtoGrade);
		}
		
		return dtoGrades;
	}

	@Override
	public DtoGrade findById(Long id) throws Exception {
		DtoGrade dtoGrade = new DtoGrade();
		Optional< Grade> optional = gradeRepository.findById(id);
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_COURSE, id.toString())) ;
		}
		Grade grade = optional.get();
		dtoGrade.setScore(grade.getScore());
		dtoGrade.setGradeId(grade.getId());
		return dtoGrade;
	}
	

}
