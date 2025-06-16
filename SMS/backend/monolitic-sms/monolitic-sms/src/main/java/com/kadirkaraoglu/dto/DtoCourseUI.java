package com.kadirkaraoglu.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.kadirkaraoglu.entities.Teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoCourseUI {
	@Size(min = 6, max = 10 ,message = "Length of the Course name must be between 6-10")
	@NotBlank(message = "Please fill the blanks")
	@NotEmpty(message = "Please fill the blanks")
	private String courseName;
	@Size(min = 20 , max = 100 ,message =  "Quota of the Course must be between 20-100")
	@NotBlank(message = "Please fill the blanks")
	@NotEmpty(message = "Please fill the blanks")
	private Long quota;
	@NotBlank(message = "Please fill the blanks")
	@NotEmpty(message = "Please fill the blanks")
	@Size(min = 1 , max = 10 ,message =  "Credit of the Course must be between 1-10")
	private int credit;
	@NotBlank(message = "Please fill the blanks")
	@NotEmpty(message = "Please fill the blanks")
	@Size(min = 1 , max = 15 ,message =  "AKTS of the Course must be between 1-15")
	private int akts;
	@NotBlank(message = "Please fill the blanks")
	@NotEmpty(message = "Please fill the blanks")
	private Teacher teacher;
	@NotBlank(message = "Please fill the blanks")
	@NotEmpty(message = "Please fill the blanks")
	private String courseCode;
	

}
