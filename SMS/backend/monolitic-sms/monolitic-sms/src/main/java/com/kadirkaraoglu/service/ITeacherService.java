package com.kadirkaraoglu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kadirkaraoglu.dto.ChangePasswordRequest;
import com.kadirkaraoglu.dto.DtoTeacher;
import com.kadirkaraoglu.dto.DtoTeacherUI;
@Service
public interface ITeacherService {
	public List<DtoTeacher> listAllTeacher();
	public DtoTeacher findTeacherById(Long id) throws Exception;
	public void deleteTeacher(Long id)throws Exception;
	public DtoTeacher updateTeacher(DtoTeacherUI dtoTeacherUI)throws Exception; 
	public DtoTeacher saveTeacher(DtoTeacherUI dtoTeacherUI) throws Exception;
	public boolean changePassword(ChangePasswordRequest changePasswordRequest) throws Exception;

}
