package com.kadirkaraoglu.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.kadirkaraoglu.dto.DtoCourse;
import com.kadirkaraoglu.dto.DtoCourseUI;
import com.kadirkaraoglu.dto.DtoStudent;
import com.kadirkaraoglu.dto.StudentCourseRequest;
@Service
public interface ICourseService {
	public List<DtoCourse> listAllCourse()throws Exception;
	public boolean addStudentTheCourses(StudentCourseRequest studentCourseRequest)throws Exception; 
	public boolean outStudentFromCourses(StudentCourseRequest studentCourseRequest)throws Exception;
	public void addStudentTheCourse(Long studentId,Long courseId )throws Exception; 
	public void outStudentFromCourse(Long studentId,Long courseId)throws Exception;
	public Set<DtoCourse> listAllCoursesOfStudent()throws Exception;
	public Set<DtoStudent> listAllStudentOnCourse(Long courseId)throws Exception;
	public DtoCourse findById(Long courseId) throws Exception;
	public void deleteCourse(Long CourseId) throws Exception;
	public DtoCourse saveCourse (DtoCourseUI courseUI);

}
