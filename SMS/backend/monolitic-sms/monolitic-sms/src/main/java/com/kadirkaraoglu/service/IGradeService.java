package com.kadirkaraoglu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kadirkaraoglu.dto.DtoGrade;
import com.kadirkaraoglu.dto.DtoGradeUI;
@Service
public interface IGradeService {
	
	public List<DtoGrade> listAllGradeOfStudent(Long studentId)throws Exception;
	public DtoGrade findById(Long id) throws Exception;
	public void updateGrade(DtoGradeUI dtoGradeUI) throws Exception;
	public DtoGrade saveGrade (DtoGradeUI gradeUI) throws Exception;

}
