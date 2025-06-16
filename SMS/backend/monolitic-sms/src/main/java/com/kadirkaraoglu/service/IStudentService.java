package com.kadirkaraoglu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kadirkaraoglu.dto.DtoStudent;
import com.kadirkaraoglu.dto.DtoStudentUI;
import com.kadirkaraoglu.entities.Student;
@Service
public interface IStudentService {
	
	public DtoStudent findStudentById(Long id)throws Exception;
	public void deleteStudent(Long id) throws Exception;
	public DtoStudent updateStudent(DtoStudentUI dtoStudentUI)throws Exception ; 
	public DtoStudent saveStudent(DtoStudentUI dtoStudentUI)throws Exception;
	public Student findStudentByUsername(String username);
	public List<DtoStudent> listAllStudent ();

}
