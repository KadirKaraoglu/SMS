package com.kadirkaraoglu.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kadirkaraoglu.dto.DtoGrade;
import com.kadirkaraoglu.dto.DtoGradeUI;
import com.kadirkaraoglu.entities.Course;
import com.kadirkaraoglu.entities.Grade;
import com.kadirkaraoglu.entities.Student;
import com.kadirkaraoglu.exception.BaseException;
import com.kadirkaraoglu.exception.ErrorMessage;
import com.kadirkaraoglu.exception.MessageType;
import com.kadirkaraoglu.repository.ICourseRepository;
import com.kadirkaraoglu.repository.IGradeRepository;
import com.kadirkaraoglu.repository.StudentRepository;
import com.kadirkaraoglu.service.IGradeService;
import com.kadirkaraoglu.service.IStudentService;
@Service
public class GradeServiceImpl implements IGradeService{

	private final IGradeRepository gradeRepository;
	private final ICourseRepository courseRepository;
	private final StudentRepository studentRepository;
	private final IStudentService studentService; 
	@Autowired
	public GradeServiceImpl(IGradeRepository gradeRepository, ICourseRepository courseRepository,
			StudentRepository studentRepository,IStudentService studentService) {
		this.gradeRepository = gradeRepository;
		this.courseRepository = courseRepository;
		this.studentRepository = studentRepository;
		this.studentService = studentService;
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
		grade.setFinalGrade(dtoGradeUI.getFinalGrade());
		grade.setMidterm(dtoGradeUI.getMidterm());
		gradeRepository.save(grade);
		
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
		Optional<Grade > optional3 = gradeRepository.findByStudent_IdAndCourse_courseId(dtoGradeUI.getStudentId(), dtoGradeUI.getCourseId()); 
		if (!optional3.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.GRADE_SAVE_ERROR,null) );
		}
		Grade grade = new Grade();
	
		grade.setFinalGrade(dtoGradeUI.getFinalGrade());
		grade.setMidterm(dtoGradeUI.getMidterm());
		grade.setCourse(optional.get());
		grade.setStudent(optional2.get());
		gradeRepository.save(grade);
		
		DtoGrade dtoGrade = new DtoGrade();
		dtoGrade.setFinalGrade(dtoGradeUI.getFinalGrade());
		dtoGrade.setMidterm(dtoGradeUI.getMidterm());
		dtoGrade.setGradeId(grade.getId());
		dtoGrade.setCourseName(optional.get().getCourseName());
		return dtoGrade;
		
	}

	@Override
	public List<DtoGrade> listAllGradeOfStudent() throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Student student = studentService.findStudentByUsername(username);
		
	    List<Grade> grades = gradeRepository.findAllByStudentId(student.getId());
	    List<DtoGrade> dtoGrades = new ArrayList<>();
	    for (Grade grade : grades) {
	        DtoGrade dtoGrade = new DtoGrade();
	        dtoGrade.setGradeId(grade.getId());
	        dtoGrade.setMidterm(grade.getMidterm());
	        dtoGrade.setFinalGrade(grade.getFinalGrade());
	        dtoGrade.setCourseName(grade.getCourse().getCourseName());
	        dtoGrade.setCourseCode(grade.getCourse().getCourseCode());
	        dtoGrade.setCourseTeacherName(grade.getCourse().getTeacher().getName());
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
		dtoGrade.setFinalGrade(grade.getFinalGrade());
		dtoGrade.setMidterm(grade.getMidterm());
		dtoGrade.setGradeId(grade.getId());
		return dtoGrade;
	}
	

}
