package com.kadirkaraoglu.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.kadirkaraoglu.dto.DtoCourse;
import com.kadirkaraoglu.dto.DtoCourseUI;
import com.kadirkaraoglu.dto.DtoStudent;
@Service
public interface ICourseService {
	public List<DtoCourse> listAllCourse()throws Exception;
	public String addStudentTheCourses(Long studentId,Set<Long> courseIds )throws Exception; 
	public String outStudentFromCourses(Long studentId,Set<Long> courseIds)throws Exception;
	public void addStudentTheCourse(Long studentId,Long courseId )throws Exception; 
	public void outStudentFromCourse(Long studentId,Long courseId)throws Exception;
	public Set<DtoCourse> listAllCoursesOfStudent(Long studentId)throws Exception;
	public Set<DtoStudent> listAllStudentOnCourse(Long courseId)throws Exception;
	public DtoCourse findById(Long courseId) throws Exception;
	public void deleteCourse(Long CourseId) throws Exception;
	public DtoCourse saveCourse (DtoCourseUI courseUI);
	public boolean isSudentOnCourse(Long studentId,Long courseId) throws Exception;

}
