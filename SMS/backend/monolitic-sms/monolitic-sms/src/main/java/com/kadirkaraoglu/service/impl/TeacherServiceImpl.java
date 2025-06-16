package com.kadirkaraoglu.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kadirkaraoglu.dto.ChangePasswordRequest;
import com.kadirkaraoglu.dto.DtoTeacher;
import com.kadirkaraoglu.dto.DtoTeacherUI;
import com.kadirkaraoglu.entities.RoleType;
import com.kadirkaraoglu.entities.Teacher;
import com.kadirkaraoglu.exception.BaseException;
import com.kadirkaraoglu.exception.ErrorMessage;
import com.kadirkaraoglu.repository.ITeacherRepository;
import com.kadirkaraoglu.service.ITeacherService;
import com.kadirkaraoglu.utils.Utils;
@Service
public class TeacherServiceImpl implements ITeacherService{
	private final ITeacherRepository teacherRepository;
	
	@Autowired
	public TeacherServiceImpl(ITeacherRepository teacherRepository) {
		
		this.teacherRepository = teacherRepository;
	}

	@Override
	public List<DtoTeacher> listAllTeacher() {
		List<Teacher> teachers = teacherRepository.findAll();
		List<DtoTeacher>  dtoTeachers = new ArrayList<DtoTeacher>();
		for (Teacher teacher : teachers) {
			DtoTeacher dtoTeacher = new DtoTeacher();
			BeanUtils.copyProperties(teacher, dtoTeacher);
			dtoTeachers.add(dtoTeacher);
		}
		return dtoTeachers;
	}

	@Override
	public DtoTeacher findTeacherById(Long id) throws Exception {
		Optional<Teacher> optional = teacherRepository.findById(id);
		if (optional.isEmpty()) {
			throw new Exception();
		}
		DtoTeacher dtoTeacher = new DtoTeacher();
		BeanUtils.copyProperties(optional.get(), dtoTeacher);
		
		return dtoTeacher;
	}

	@Override
	public void deleteTeacher(Long id) throws Exception {
		Optional<Teacher> optional = teacherRepository.findById(id);
		if (optional.isEmpty()) {
			throw new Exception();
		}
		teacherRepository.deleteById(id);
		
	}

	@Override
	public DtoTeacher updateTeacher(DtoTeacherUI dtoTeacherUI) throws Exception {
		DtoTeacher dtoTeacher = new DtoTeacher();
		Optional<Teacher> optional = teacherRepository.findById(dtoTeacherUI.getId());
		if (optional.isEmpty()) {
			throw new Exception();//ogrecı bulunamadı
		}
		Teacher teacher= optional.get();
		BeanUtils.copyProperties(teacher, dtoTeacher);
		teacherRepository.save(teacher);
		
		
		
		return dtoTeacher;
	}

	@Override
	public DtoTeacher saveTeacher(DtoTeacherUI dtoTeacherUI) throws Exception {
		Teacher teacher = new Teacher();
		teacher.setBirthDate(dtoTeacherUI.getBirthDate());
		teacher.setName(dtoTeacherUI.getName());
		teacher.setSurname(dtoTeacherUI.getSurname());
		if( !Utils.isValidTcNo(dtoTeacherUI.getTcNo())) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.INVALID_TCKN, dtoTeacherUI.toString())) ;
		}
		teacher.setTcNo(dtoTeacherUI.getTcNo());
		teacher.setRole(RoleType.ROLE_TEACHER);
		teacher.setUsername(Utils.personIdCreator(000L, dtoTeacherUI.getTcNo()));
		teacher.setPassword(Utils.defualtPasswordCreator(dtoTeacherUI.getTcNo()));
		teacherRepository.save(teacher);
		
		DtoTeacher dtoTeacher = new DtoTeacher();
		BeanUtils.copyProperties(teacher, dtoTeacher);
		
		return dtoTeacher;
	}

	@Override
	public boolean changePassword(ChangePasswordRequest changePasswordRequest) throws Exception {
		Optional<Teacher> optional = teacherRepository.findById(changePasswordRequest.getId());
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_TEACHER, changePasswordRequest.toString())) ;
		}
		if (!changePasswordRequest.getOldpassword().equals(optional.get().getPassword()) ) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.OLD_PASSWORD_IS_WRONG, changePasswordRequest.toString())) ;
		}
		optional.get().setPassword(changePasswordRequest.getNewPassword());
		return true;
	
		
	}

}
