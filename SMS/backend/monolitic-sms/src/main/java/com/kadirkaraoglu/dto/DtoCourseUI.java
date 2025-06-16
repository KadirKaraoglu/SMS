package com.kadirkaraoglu.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoCourseUI {
	@Size(min = 3, max = 40 ,message = "Length of the Course name must be between 6-10")
	@NotBlank(message = "Please fill the blanks")
	@NotEmpty(message = "Please fill the blanks")
	private String courseName;
	@Min(value =  20  ,message =  "Quota of the Course must be between 20-100")
	@Max(value =  100  ,message =  "Quota of the Course must be between 20-100")
	private Long quota;
	@Min(value  = 1 ,message =  "Credit of the Course must be between 1-10")
	@Max(value  = 10 ,message =  "Credit of the Course must be between 1-10")
	private int credit;

	@Min(value =   1 ,message =  "AKTS of the Course must be between 1-15")
	@Max(value =   15 ,message =  "AKTS of the Course must be between 1-15")
	private int akts;
	
	private Long teacherId;

	private String courseCode;
	

}
