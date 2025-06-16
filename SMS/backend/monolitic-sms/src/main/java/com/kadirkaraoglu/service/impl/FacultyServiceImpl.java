package com.kadirkaraoglu.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kadirkaraoglu.entities.Faculty;
import com.kadirkaraoglu.exception.BaseException;
import com.kadirkaraoglu.exception.ErrorMessage;
import com.kadirkaraoglu.exception.MessageType;
import com.kadirkaraoglu.repository.IFacultyRepository;
import com.kadirkaraoglu.service.IFacultyService;
@Service
public class FacultyServiceImpl implements IFacultyService{
	
	private final IFacultyRepository facultyRepository;
	@Autowired
	public FacultyServiceImpl(IFacultyRepository facultyRepository) {
	
		this.facultyRepository = facultyRepository;
	}

	@Override
	public Faculty findFacultyById(Long id) throws Exception {
		Optional<Faculty> optional =  facultyRepository.findById(id);
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST_FACULTY, null));
		}
		return optional.get();
	}

	@Override
	public List<Faculty> listFaculties() {
		   
		return facultyRepository.findAll();
	}

}
