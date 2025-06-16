package com.kadirkaraoglu.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kadirkaraoglu.dto.DtoCourse;
import com.kadirkaraoglu.dto.DtoCourseUI;
import com.kadirkaraoglu.dto.DtoFaculty;
import com.kadirkaraoglu.dto.DtoStudent;
import com.kadirkaraoglu.dto.DtoTeacher;
import com.kadirkaraoglu.dto.StudentCourseRequest;
import com.kadirkaraoglu.entities.Course;
import com.kadirkaraoglu.entities.Student;
import com.kadirkaraoglu.entities.Teacher;
import com.kadirkaraoglu.exception.BaseException;
import com.kadirkaraoglu.exception.ErrorMessage;
import com.kadirkaraoglu.exception.MessageType;
import com.kadirkaraoglu.repository.ICourseRepository;
import com.kadirkaraoglu.repository.ITeacherRepository;
import com.kadirkaraoglu.repository.StudentRepository;
import com.kadirkaraoglu.service.ICourseService;
import com.kadirkaraoglu.service.IStudentService;

import jakarta.transaction.Transactional;
@Service
public class CourseServiceImpl implements ICourseService{
	private final ICourseRepository courseRepository;
	private final StudentRepository studentRepository;
	private final ITeacherRepository teacherRepository;
	private final IStudentService studentService;
@Autowired
	public CourseServiceImpl(ICourseRepository courseRepository, IStudentService studentService,StudentRepository studentRepository,ITeacherRepository teacherRepository) {
		this.teacherRepository = teacherRepository;
		this.courseRepository = courseRepository;
		this.studentRepository = studentRepository;
		this.studentService = studentService;
	}

	@Override
	public List<DtoCourse> listAllCourse() {
		 List<DtoCourse> dtoCourses = new ArrayList<DtoCourse>();
		 for (Course course : courseRepository.findAll()) {
			DtoCourse dtoCourse = new DtoCourse();
			BeanUtils.copyProperties(course, dtoCourse);
			dtoCourses.add(dtoCourse);
			dtoCourse.setTeacherName(course.getTeacher().getName());
			dtoCourse.setAkts(course.getAkts());
			dtoCourse.setCredit(course.getCredit());
		}
		return dtoCourses;
	}

	@Override
	public DtoCourse findById(Long id) throws Exception {
		Optional<Course> optional = courseRepository.findById(id); 
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST_COURSE, null));
		}
		DtoCourse dtoCourse = new DtoCourse();
		BeanUtils.copyProperties(optional.get(), dtoCourse);
	
		dtoCourse.setTeacherName(optional.get().getTeacher().getName());
		return dtoCourse;
	}

	@Override
	public void deleteCourse(Long id) throws Exception {
		Optional<Course> optional = courseRepository.findById(id); 
		if (optional.isEmpty()) {
			throw new Exception();
		}
		courseRepository.deleteById(id);
		
	}

	@Override
	public DtoCourse saveCourse(DtoCourseUI courseUI) {
		DtoCourse dtoCourse = new DtoCourse();
		Course course =new Course();
		Optional<Teacher > optional = teacherRepository.findById(courseUI.getTeacherId());
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_TEACHER, courseUI.getTeacherId().toString()));
		}
		course.setAkts(courseUI.getAkts());
		course.setCourseCode(courseUI.getCourseCode());
		course.setCourseName(courseUI.getCourseName());
		course.setCredit(courseUI.getCredit());
		course.setQuota(courseUI.getQuota());
		course.setTeacher(optional.get());
		courseRepository.save(course);
		BeanUtils.copyProperties(course, dtoCourse);
		DtoTeacher dtoTeacher = new DtoTeacher();
		BeanUtils.copyProperties(optional.get(), dtoTeacher);
		dtoCourse.setTeacherName(dtoTeacher.getName());
	
		return dtoCourse;
	}

	@Override
	public Set<DtoCourse> listAllCoursesOfStudent() throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Student student = studentService.findStudentByUsername(username);
		Set<DtoCourse> dtoCourses = new HashSet<DtoCourse>();
		Optional<Student> optional = studentRepository.findById(student.getId());
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT, student.getId().toString())) ;
		}
		Set<Course> courses = student.getCourses();
		for (Course  course : courses) {
			DtoCourse dtoCourse = findById(course.getCourseId());
			dtoCourse.setTeacherName(course.getTeacher().getName());
		

		dtoCourses.add(dtoCourse);
		}
		
		return dtoCourses;
	}

	@Override
	@Transactional
	public boolean addStudentTheCourses(StudentCourseRequest studentCourseRequest) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Student student = studentService.findStudentByUsername(username);
		
		for (Long long1 : studentCourseRequest.getCourseIds()) {
			addStudentTheCourse(student.getId(), long1);
		}
		
		return true;
	}

	@Override
	@Transactional
	public boolean outStudentFromCourses(StudentCourseRequest studentCourseRequest) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Student student = studentService.findStudentByUsername(username);
		
		
		for (Long long1 : studentCourseRequest.getCourseIds()) {
			if (!studentRepository.isStudentEnrolledInCourse(student.getId(), long1)) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.YOU_DONT_TAKE_THIS_COURSE,null)) ;
			} 
			outStudentFromCourse(student.getId(), long1);
		}
		return true;
	}
	
	String whichCourseIsSame;



	@Override
	@Transactional
	public void addStudentTheCourse(Long studentId, Long courseId) throws Exception {
	    Optional<Student> optional = studentRepository.findById(studentId);
	    if (optional.isEmpty()) {
	        throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT, studentId.toString())) ;
	    }

	    Optional<Course> optional2 = courseRepository.findById(courseId);
	    if (optional2.isEmpty()) {
	        throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_COURSE, courseId.toString())) ;
	    }

	    Course course = optional2.get(); 
	    Student student = optional.get();

	    if (studentRepository.isStudentEnrolledInCourse(studentId, courseId)) {
	        throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.YOU_ALREADY_TAKE_THIS_COURSE, course.getCourseName()));
	    }

	    if (course.getSumOfCurrentStudent() + 1 > course.getQuota()) {
	        throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.COURSE_IS_FULL, course.getCourseName()));
	    }

	   
	    student.getCourses().add(course);
	    course.setSumOfCurrentStudent(course.getSumOfCurrentStudent() + 1);

	  
	    studentRepository.save(student);  
	    courseRepository.save(course);  
	}




	@Override
	@Transactional
	public void outStudentFromCourse(Long studentId, Long courseId) throws Exception {
		Optional<Student> optional = studentRepository.findById(studentId);
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT, studentId.toString())) ;
		}
		Optional<Course> optional2 = courseRepository.findById(courseId);
		if (optional2.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_COURSE, courseId.toString())) ;
		}
		Course course = optional2.get(); 
		Student student =  optional.get();
		Set<Course> courses = student.getCourses();
		if (!courses.contains(course)) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.YOU_DONT_TAKE_THIS_COURSE, course.getCourseName())) ;
		}
		courses.remove(course);
		student.setCourses(courses);
		studentRepository.save(student);
			
		course.setSumOfCurrentStudent(course.getSumOfCurrentStudent()-1);
		courseRepository.save(course);
	}

	@Override
	public Set<DtoStudent> listAllStudentOnCourse(Long courseId) throws Exception {
		Optional<Course> optional2 = courseRepository.findById(courseId);
		if (optional2.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_COURSE, courseId.toString())) ;
		}
		Course course = optional2.get(); 
		Set<Student> students = course.getStudents();
 		Set<DtoStudent> dtoStudents = new HashSet<DtoStudent>();
 		for (Student student : students) {
			DtoStudent dtoStudent = new  DtoStudent();
		
			dtoStudent.setId(student.getId());
			dtoStudent.setName(student.getName());
			dtoStudent.setUsername(student.getUsername());
			dtoStudent.setSurname(student.getSurname());
		
			dtoStudent.setFacultyName(student.getFaculty().getFacultyname());
			dtoStudents.add(dtoStudent);
			
		}
		return dtoStudents;
	}

}
