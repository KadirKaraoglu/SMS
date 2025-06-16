package com.kadirkaraoglu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DtoGrade {
	private String courseName;
	private String courseCode;
	private String courseTeacherName;
	
	private int midterm ;
	
	private int finalGrade;
	
	private Long gradeId;

}
