package com.kadirkaraoglu.service.impl;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kadirkaraoglu.entities.Student;
import com.kadirkaraoglu.entities.Teacher;
import com.kadirkaraoglu.exception.BaseException;
import com.kadirkaraoglu.exception.ErrorMessage;
import com.kadirkaraoglu.repository.ITeacherRepository;
import com.kadirkaraoglu.repository.StudentRepository;
import com.kadirkaraoglu.security.JwtUserDetails;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final StudentRepository studentRepository; 
	private final ITeacherRepository teacherRepository;
	public UserDetailsServiceImpl(StudentRepository studentRepository, ITeacherRepository teacherRepository) {
		this.studentRepository = studentRepository;
		this.teacherRepository = teacherRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Teacher teacher = teacherRepository.findTeacherByUsername(username);
		Student student =studentRepository.findStudentByUsername(username);
		
		if (student==null && teacher== null) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST,username.toString())) ;
		}
		if (teacher != null) {
			return JwtUserDetails.create(teacher);
		}
		else {
			return JwtUserDetails.create(student);
		}
		
		
		
	}
	public UserDetails loadUserbyId(Long id) throws Exception{
		Optional<Teacher> teacher = teacherRepository.findById(id);
		Optional<Student> student =studentRepository.findById(id);
		if (student.isEmpty() && teacher.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT,id.toString())) ;
		}
		if (teacher != null) {
			return JwtUserDetails.create(teacher.get());
		}
		else {
			return JwtUserDetails.create(student.get());
		}
	}

}
