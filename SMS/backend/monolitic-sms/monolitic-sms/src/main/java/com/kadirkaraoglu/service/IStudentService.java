package com.kadirkaraoglu.service;

import org.springframework.stereotype.Service;

import com.kadirkaraoglu.dto.ChangePasswordRequest;
import com.kadirkaraoglu.dto.DtoStudent;
import com.kadirkaraoglu.dto.DtoStudentUI;
@Service
public interface IStudentService {
	
	public DtoStudent findStudentById(Long id)throws Exception;
	public void deleteStudent(Long id) throws Exception;
	public DtoStudent updateStudent(DtoStudentUI dtoStudentUI)throws Exception ; 
	public DtoStudent saveStudent(DtoStudentUI dtoStudentUI)throws Exception;
	public boolean changePassword(ChangePasswordRequest changePasswordRequest)throws Exception;
	public boolean isFirstLogin (String personId);

}
