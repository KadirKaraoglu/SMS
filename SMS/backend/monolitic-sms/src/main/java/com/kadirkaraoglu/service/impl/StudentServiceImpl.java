package com.kadirkaraoglu.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kadirkaraoglu.dto.DtoCourse;
import com.kadirkaraoglu.dto.DtoFaculty;
import com.kadirkaraoglu.dto.DtoStudent;
import com.kadirkaraoglu.dto.DtoStudentUI;
import com.kadirkaraoglu.entities.Course;
import com.kadirkaraoglu.entities.RoleType;
import com.kadirkaraoglu.entities.Student;
import com.kadirkaraoglu.exception.BaseException;
import com.kadirkaraoglu.exception.ErrorMessage;
import com.kadirkaraoglu.exception.MessageType;
import com.kadirkaraoglu.repository.ITeacherRepository;
import com.kadirkaraoglu.repository.StudentRepository;
import com.kadirkaraoglu.service.IFacultyService;
import com.kadirkaraoglu.service.IStudentService;
import com.kadirkaraoglu.utils.Utils;
@Service
public class StudentServiceImpl implements IStudentService{

	private final StudentRepository studentRepository;
	private final IFacultyService facultyServiceImpl;
	private final BCryptPasswordEncoder passwordEncoder;
	private final ITeacherRepository teacherRepository;

	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository, IFacultyService facultyServiceImpl,BCryptPasswordEncoder passwordEncoder
			,ITeacherRepository teacherRepository) {
		this.teacherRepository =  teacherRepository;
		this.studentRepository = studentRepository;
		this.facultyServiceImpl = facultyServiceImpl;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public DtoStudent findStudentById(Long id) {
		Optional<Student> optional = studentRepository.findById(id);
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT, id.toString())) ;
		}
		Student student = optional.get();
		DtoStudent dtoStudent = new DtoStudent() ;
		BeanUtils.copyProperties(student, dtoStudent);
		Set<DtoCourse> dtoCourses = new HashSet<DtoCourse>(); 
		for (Course course : student.getCourses()) {
		DtoCourse dtoCourse = new DtoCourse();
		BeanUtils.copyProperties(course, dtoCourse);
		dtoCourses.add(dtoCourse);
		
		} 
		
		dtoStudent.setCourses(dtoCourses);
		
		dtoStudent.setFacultyName(student.getFaculty().getFacultyname());
		return dtoStudent;
	}

	@Override
	public void deleteStudent(Long id) throws Exception {
		Optional<Student> optional = studentRepository.findById(id);
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT, id.toString())) ;
		}
		studentRepository.deleteById(optional.get().getId());
	}

	@Override
	public DtoStudent updateStudent(DtoStudentUI dtoStudentUI) throws Exception {
		DtoStudent dtoStudent = new DtoStudent();
		Optional<Student> optional = studentRepository.findStudentByTcNo(dtoStudentUI.getTcNo());
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT, dtoStudentUI.toString())) ;
		}
		Student student = optional.get();
	
		BeanUtils.copyProperties(student, dtoStudent);
		studentRepository.save(student);
		
		
		return dtoStudent;
	}

	@Override
	public DtoStudent saveStudent(DtoStudentUI dtoStudentUI) throws Exception {
		Student student = new Student();
		student.setBirthDate(dtoStudentUI.getBirthDate());
		student.setName(dtoStudentUI.getName());
		student.setSurname(dtoStudentUI.getSurname());
		if( !Utils.isValidTcNo(dtoStudentUI.getTcNo())) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.INVALID_TCKN, dtoStudentUI.toString())) ;
		}
		if (!(studentRepository.findStudentByTcNo(dtoStudentUI.getTcNo())).isEmpty() || !teacherRepository.findTeacherByTcNo(dtoStudentUI.getTcNo()).isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.TCKN_ALREADY_EXIST,dtoStudentUI.getTcNo()));
		}		
		student.setTcNo(dtoStudentUI.getTcNo());
		student.setFaculty(facultyServiceImpl.findFacultyById(dtoStudentUI.getFacultyId()));
		student.setRole(RoleType.ROLE_STUDENT);
		student.setPassword(passwordEncoder.encode(Utils.defualtPasswordCreator(dtoStudentUI.getTcNo())));
		student.setUsername( Utils.usernameCreator(dtoStudentUI.getFacultyId(), dtoStudentUI.getTcNo()));
		studentRepository.save(student);
		
		DtoStudent dtoStudent = new DtoStudent();
		BeanUtils.copyProperties(student, dtoStudent);
		DtoFaculty dtoFaculty = new DtoFaculty();
		dtoFaculty.setFacultyId(dtoStudentUI.getFacultyId());
		dtoStudent.setFacultyName(facultyServiceImpl.findFacultyById(dtoStudentUI.getFacultyId()).getFacultyname());
	
		return dtoStudent;
	}

	@Override
	public Student findStudentByUsername(String username) {
		Student student =studentRepository.findStudentByUsername(username);
	
		return student;
	}

	@Override
	public List<DtoStudent> listAllStudent() {
		List<DtoStudent> dtoStudents = new  ArrayList<DtoStudent>();
		List<Student> students =  studentRepository.findAll();
		for (Student student : students) {
			DtoStudent dtoStudent = new DtoStudent();
			BeanUtils.copyProperties(student, dtoStudent);
			dtoStudent.setFacultyName(student.getFaculty().getFacultyname());
			dtoStudents.add(dtoStudent);
		}
		return dtoStudents;
	}




}
