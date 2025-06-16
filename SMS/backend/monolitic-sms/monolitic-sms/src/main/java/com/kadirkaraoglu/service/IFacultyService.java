package com.kadirkaraoglu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kadirkaraoglu.entities.Faculty;

@Service
public interface IFacultyService {

 
	public Faculty findFacultyById(Long id)throws Exception;
	public List<Faculty> listFaculties(); 
}
