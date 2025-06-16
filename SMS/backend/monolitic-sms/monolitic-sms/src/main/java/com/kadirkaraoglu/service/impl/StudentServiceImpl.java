package com.kadirkaraoglu.service.impl;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kadirkaraoglu.dto.ChangePasswordRequest;
import com.kadirkaraoglu.dto.DtoFaculty;
import com.kadirkaraoglu.dto.DtoStudent;
import com.kadirkaraoglu.dto.DtoStudentUI;
import com.kadirkaraoglu.entities.Role;
import com.kadirkaraoglu.entities.RoleType;
import com.kadirkaraoglu.entities.Student;
import com.kadirkaraoglu.exception.BaseException;
import com.kadirkaraoglu.exception.ErrorMessage;
import com.kadirkaraoglu.repository.StudentRepository;
import com.kadirkaraoglu.service.IFacultyService;
import com.kadirkaraoglu.service.IStudentService;
import com.kadirkaraoglu.utils.Utils;
@Service
public class StudentServiceImpl implements IStudentService{

	private final StudentRepository studentRepository;
	private final IFacultyService facultyServiceImpl;
	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public StudentServiceImpl(StudentRepository studentRepository, IFacultyService facultyServiceImpl,BCryptPasswordEncoder passwordEncoder) {
		
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
		dtoStudent.setCourses(student.getCourses());
		DtoFaculty dtoFaculty = new DtoFaculty();
		dtoFaculty.setFacultyName(student.getFaculty().getFacultyname());
		dtoStudent.setDtoFaculty(dtoFaculty);
		return dtoStudent;
	}

	@Override
	public void deleteStudent(Long id) throws Exception {
		Optional<Student> optional = studentRepository.findById(id);
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT, id.toString())) ;
		}
		studentRepository.deleteById(id);
	}

	@Override
	public DtoStudent updateStudent(DtoStudentUI dtoStudentUI) throws Exception {
		DtoStudent dtoStudent = new DtoStudent();
		Student student = studentRepository.findStudentByTcNo(dtoStudentUI.getTcNo());
	
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
				
		student.setTcNo(dtoStudentUI.getTcNo());
		student.setFaculty(facultyServiceImpl.findFacultyById(dtoStudentUI.getFacultyId()));
		student.setRole(RoleType.ROLE_STUDENT);
		student.setPassword(passwordEncoder.encode(Utils.defualtPasswordCreator(dtoStudentUI.getTcNo())));
		student.setUsername( Utils.personIdCreator(dtoStudentUI.getFacultyId(), dtoStudentUI.getTcNo()));
		studentRepository.save(student);
		
		DtoStudent dtoStudent = new DtoStudent();
		BeanUtils.copyProperties(student, dtoStudent);
		DtoFaculty dtoFaculty = new DtoFaculty();
		dtoFaculty.setFacultyId(dtoStudentUI.getFacultyId());
		dtoFaculty.setFacultyName(facultyServiceImpl.findFacultyById(dtoStudentUI.getFacultyId()).getFacultyname());
		dtoStudent.setDtoFaculty(dtoFaculty);
	
		return dtoStudent;
	}

	@Override
	public boolean changePassword(ChangePasswordRequest changePasswordRequest) throws Exception {
		Optional<Student> optional = studentRepository.findById(changePasswordRequest.getId());
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT, changePasswordRequest.toString())) ;
		}
		if (!changePasswordRequest.getOldpassword().equals(optional.get().getPassword()) ) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.OLD_PASSWORD_IS_WRONG, changePasswordRequest.toString())) ;
		}
	
		optional.get().setPassword(changePasswordRequest.getNewPassword());
		return true;
	}

	@Override
	public boolean isFirstLogin(String personId) {
		Student student = studentRepository.findStudentByUsername(personId);
		 if (student.isFirstLogin()) {
			 student.setFirstLogin(false);
			 studentRepository.save(student);
			 return true;
		 }	
		return false;
	}

}
