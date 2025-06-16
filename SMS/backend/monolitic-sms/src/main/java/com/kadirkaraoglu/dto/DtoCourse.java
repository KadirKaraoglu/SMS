package com.kadirkaraoglu.dto;

import java.util.Set;

import com.kadirkaraoglu.entities.Teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoCourse {
	private Long courseId;
	
	private String courseName;
	
	private Long quota;
	private String courseCode;
	
	private int SumOfCurrentStudent;

	private String teacherName;
	
	private int akts;
	
	private int credit;
	
	private Set<DtoStudent> dtoStudents;


}
