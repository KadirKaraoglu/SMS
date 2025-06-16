package com.kadirkaraoglu.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kadirkaraoglu.dto.DtoCourse;
import com.kadirkaraoglu.dto.DtoTeacher;
import com.kadirkaraoglu.dto.DtoTeacherUI;
import com.kadirkaraoglu.entities.Teacher;
@Service
public interface ITeacherService {
	public List<DtoTeacher> listAllTeacher();
	public DtoTeacher findTeacherById(Long id) throws Exception;
	public void deleteTeacher(Long id)throws Exception;
	public DtoTeacher updateTeacher(DtoTeacherUI dtoTeacherUI)throws Exception; 
	public DtoTeacher saveTeacher(DtoTeacherUI dtoTeacherUI) throws Exception;
	public Teacher findTeacherByUsername(String username);
	 public Set<DtoCourse>  listourseOfTecaher(); 
	

}
