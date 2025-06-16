package com.kadirkaraoglu.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kadirkaraoglu.dto.DtoCourse;
import com.kadirkaraoglu.dto.DtoCourseUI;
import com.kadirkaraoglu.dto.DtoFaculty;
import com.kadirkaraoglu.dto.DtoStudent;
import com.kadirkaraoglu.dto.DtoTeacher;
import com.kadirkaraoglu.entities.Course;
import com.kadirkaraoglu.entities.Student;
import com.kadirkaraoglu.exception.BaseException;
import com.kadirkaraoglu.exception.ErrorMessage;
import com.kadirkaraoglu.repository.ICourseRepository;
import com.kadirkaraoglu.repository.StudentRepository;
import com.kadirkaraoglu.service.ICourseService;
@Service
public class CourseServiceImpl implements ICourseService{
	private final ICourseRepository courseRepository;
	private final StudentRepository studentRepository;
@Autowired
	public CourseServiceImpl(ICourseRepository courseRepository, StudentRepository studentRepository) {

		this.courseRepository = courseRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	public List<DtoCourse> listAllCourse() {
		 List<DtoCourse> dtoCourses = new ArrayList<DtoCourse>();
		 for (Course course : courseRepository.findAll()) {
			DtoCourse dtoCourse = new DtoCourse();
			BeanUtils.copyProperties(course, dtoCourse);
			dtoCourses.add(dtoCourse);
		}
		return dtoCourses;
	}

	@Override
	public DtoCourse findById(Long id) throws Exception {
		Optional<Course> optional = courseRepository.findById(id); 
		if (optional.isEmpty()) {
			throw new Exception();
		}
		DtoCourse dtoCourse = new DtoCourse();
		BeanUtils.copyProperties(optional.get(), dtoCourse);
		DtoTeacher dtoTeacher = new DtoTeacher();
		dtoTeacher.setId(optional.get().getTeacher().getId());
		dtoTeacher.setName(optional.get().getTeacher().getName());
		dtoCourse.setTeacher(dtoTeacher);
		Set<DtoStudent> dtoStudents = new HashSet<DtoStudent>();
		dtoStudents = listAllStudentOnCourse(id);
		dtoCourse.setStudents(dtoStudents);
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
		BeanUtils.copyProperties(courseUI, course);
		courseRepository.save(course);
		BeanUtils.copyProperties(course, dtoCourse);
		return dtoCourse;
	}

	@Override
	public Set<DtoCourse> listAllCoursesOfStudent(Long studentId) throws Exception {
		Set<DtoCourse> dtoCourses = new HashSet<DtoCourse>();
		Optional<Student> optional = studentRepository.findById(studentId);
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT, studentId.toString())) ;
		}
		Student student = optional.get();
		Set<Course> courses = student.getCourses();
		for (Course  course : courses) {
			DtoCourse dtoCourse = findById(course.getCourseId());
		dtoCourses.add(dtoCourse);
		}
		
		return dtoCourses;
	}

	@Override
	public String addStudentTheCourses(Long studentId, Set<Long> courseIds) throws Exception {
		Optional<Student> optional = studentRepository.findById(studentId);
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT, studentId.toString())) ;
		}
		
		for (Long long1 : courseIds) {
			if (isSudentOnCourse(studentId, long1)) {
			return "you have already take course";
			} 
			addStudentTheCourse(studentId, long1);
		}
		
		return "You are enrolled all course you seleted successfully";
	}

	@Override
	public String outStudentFromCourses(Long studentId, Set<Long> courseIds) throws Exception {
		Optional<Student> optional = studentRepository.findById(studentId);
		if (optional.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT, studentId.toString())) ;
		}
		
		for (Long long1 : courseIds) {
			if (!isSudentOnCourse(studentId, long1)) {
			return "Already you does not take course";
			} 
			outStudentFromCourse(studentId, long1);
		}
		return "You are taken out all courses you seleted successfully";
	}
	
	String whichCourseIsSame;
	@Override
	public boolean isSudentOnCourse(Long studentId, Long courseId) throws Exception {
		Set<DtoCourse> dtoCourses = listAllCoursesOfStudent(studentId);
		Optional<Course> optional2 = courseRepository.findById(courseId);
		if (optional2.isEmpty()) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_COURSE, courseId.toString())) ;
		}
			    boolean isSameCourse = false;
			    for (DtoCourse dtoCourse : dtoCourses) {
			    if (dtoCourse.getCourseId() == optional2.get().getCourseId() ) {
			            isSameCourse = true;
			            whichCourseIsSame = dtoCourse.getCourseName();
			            break;
			           }
			    }
		
		
		return isSameCourse;
	}

	@Override
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
		
		if (isSudentOnCourse(studentId, courseId)) {
			throw new  Exception("You have already take " + whichCourseIsSame)  ;
		} 
		
		if (1+course.getQuota()> course.getQuota()) {
			throw new  Exception("Quota of "+ course.getCourseName() +" is full");
		}
		optional.get().getCourses().add(course);	
		
	}

	@Override
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
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.YOU_DONT_TAKE_THIS_COURSE, courseId.toString())) ;
		}
		courses.remove(course);
		
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
			dtoStudent.setBirthDate(student.getBirthDate());
			dtoStudent.setId(student.getId());
			dtoStudent.setName(student.getName());
			dtoStudent.setUsername(student.getUsername());
			dtoStudent.setSurname(student.getSurname());
			dtoStudent.setTcNo(student.getTcNo());
			DtoFaculty dtoFaculty = new DtoFaculty();
			dtoFaculty.setFacultyName(student.getFaculty().getFacultyname());
			dtoStudent.setDtoFaculty(dtoFaculty);
			dtoStudents.add(dtoStudent);
			
		}
		return dtoStudents;
	}

}
