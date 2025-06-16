package com.kadirkaraoglu.service.impl;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kadirkaraoglu.dto.DtoCourse;
import com.kadirkaraoglu.dto.DtoStudent;
import com.kadirkaraoglu.dto.DtoTeacher;
import com.kadirkaraoglu.dto.DtoTeacherUI;
import com.kadirkaraoglu.entities.Course;
import com.kadirkaraoglu.entities.RoleType;
import com.kadirkaraoglu.entities.Student;
import com.kadirkaraoglu.entities.Teacher;
import com.kadirkaraoglu.exception.BaseException;
import com.kadirkaraoglu.exception.ErrorMessage;
import com.kadirkaraoglu.exception.MessageType;
import com.kadirkaraoglu.repository.ITeacherRepository;
import com.kadirkaraoglu.repository.StudentRepository;
import com.kadirkaraoglu.service.ITeacherService;
import com.kadirkaraoglu.utils.Utils;
@Service
public class TeacherServiceImpl implements ITeacherService{
	private final ITeacherRepository teacherRepository;
	private final PasswordEncoder encoder;
	private final StudentRepository studentRepository;
	
	@Autowired
	public TeacherServiceImpl(ITeacherRepository teacherRepository,PasswordEncoder encoder,StudentRepository studentRepository) {
		this.encoder =encoder;
		this.teacherRepository = teacherRepository;
		this.studentRepository =studentRepository;
	}

	@Override
	public List<DtoTeacher> listAllTeacher() {
		List<Teacher> teachers = teacherRepository.findAll();
		List<DtoTeacher>  dtoTeachers = new ArrayList<DtoTeacher>();
		for (Teacher teacher : teachers) {
			DtoTeacher dtoTeacher = new DtoTeacher();
			BeanUtils.copyProperties(teacher, dtoTeacher);
			dtoTeachers.add(dtoTeacher);
		}
		return dtoTeachers;
	}

	@Override
	public DtoTeacher findTeacherById(Long id) throws Exception {
		Optional<Teacher> optional = teacherRepository.findById(id);
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST_TEACHER, id.toString()));
		}
		DtoTeacher dtoTeacher = new DtoTeacher();
		BeanUtils.copyProperties(optional.get(), dtoTeacher);
		
		return dtoTeacher;
	}

	@Override
	public void deleteTeacher(Long id) throws Exception {
		Optional<Teacher> optional = teacherRepository.findById(id);
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST_TEACHER, id.toString()));
		}
		teacherRepository.deleteById(id);
		
	}

	@Override
	public DtoTeacher updateTeacher(DtoTeacherUI dtoTeacherUI) throws Exception {
		DtoTeacher dtoTeacher = new DtoTeacher();
		Optional<Teacher> optional = teacherRepository.findById(dtoTeacherUI.getId());
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST_TEACHER, dtoTeacherUI.toString()));
		}
		Teacher teacher= optional.get();
		BeanUtils.copyProperties(teacher, dtoTeacher);
		teacherRepository.save(teacher);
		
		
		
		return dtoTeacher;
	}

	@Override
	public DtoTeacher saveTeacher(DtoTeacherUI dtoTeacherUI) throws Exception {
		Teacher teacher = new Teacher();
		teacher.setBirthDate(dtoTeacherUI.getBirthDate());
		teacher.setName(dtoTeacherUI.getName());
		teacher.setSurname(dtoTeacherUI.getSurname());
		if( !Utils.isValidTcNo(dtoTeacherUI.getTcNo())) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.INVALID_TCKN, dtoTeacherUI.toString())) ;
		}
		if (!teacherRepository.findTeacherByTcNo(dtoTeacherUI.getTcNo()).isEmpty() || !(studentRepository.findStudentByTcNo(dtoTeacherUI.getTcNo())).isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.TCKN_ALREADY_EXIST,dtoTeacherUI.getTcNo()));
		}
		teacher.setTcNo(dtoTeacherUI.getTcNo());
		teacher.setRole(RoleType.ROLE_TEACHER);
		teacher.setUsername(Utils.usernameCreator(000L, dtoTeacherUI.getTcNo()));
		teacher.setPassword(encoder.encode(Utils.defualtPasswordCreator(dtoTeacherUI.getTcNo())) );
		teacherRepository.save(teacher);
		
		DtoTeacher dtoTeacher = new DtoTeacher();
		BeanUtils.copyProperties(teacher, dtoTeacher);
		
		return dtoTeacher;
	}

	@Override
	public Teacher findTeacherByUsername(String username) {
		Teacher teacher = teacherRepository.findTeacherByUsername(username);
		if (teacher== null) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_TEACHER, username.toString())) ;
		}
		return teacher;
	}

	@Override
	public Set<DtoCourse> listourseOfTecaher() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Teacher teacher = findTeacherByUsername(username);
		Set<DtoCourse> dtoCourses = new HashSet<DtoCourse>();
		Set<DtoStudent> dtoStudents = new HashSet<DtoStudent>();
 		
		 for (Course  course :teacher.getCourses() ) {
			 
			 for (Student student : course.getStudents()) {
				 DtoStudent dtoStudent = new DtoStudent();
				 BeanUtils.copyProperties(student, dtoStudent);
				 dtoStudents.add(dtoStudent);
			}
			 DtoCourse dtoCourse = new DtoCourse();
			BeanUtils.copyProperties(course, dtoCourse);
			dtoCourse.setDtoStudents(dtoStudents);
			dtoCourses.add(dtoCourse);
	
			
		} 
		
		return dtoCourses;
	}


}
