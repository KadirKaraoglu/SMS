package com.kadirkaraoglu.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kadirkaraoglu.entities.Student;
import com.kadirkaraoglu.exception.BaseException;
import com.kadirkaraoglu.exception.ErrorMessage;
import com.kadirkaraoglu.repository.StudentRepository;
import com.kadirkaraoglu.security.JwtUserDetails;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final StudentRepository studentRepository; 
	public UserDetailsServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	@Override
	public UserDetails loadUserByUsername(String personId) throws UsernameNotFoundException {
		if (studentRepository.findStudentByUsername(personId)==null) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT,personId.toString())) ;
		}
		Student t =  studentRepository.findStudentByUsername(personId);
		
		return JwtUserDetails.create(t);
		
	}
	public UserDetails loadUserbyId(Long id) throws Exception{
		
		if (studentRepository.findById(id).get()==null) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT, id.toString())) ;
		}
		Student t =  studentRepository.findById(id).get();
		
		return JwtUserDetails.create(t);
		
	}

}
