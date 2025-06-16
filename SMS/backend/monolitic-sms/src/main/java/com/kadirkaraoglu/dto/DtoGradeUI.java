package com.kadirkaraoglu.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoGradeUI {
	@Min (value = 0, message = "Grade must be between 0-100")
	@Max (value = 100, message = "Grade must be between 0-100")
	private int midterm ;
	@Min (value = 0, message = "Grade must be between 0-100")
	@Max (value = 100, message = "Grade must be between 0-100")
	private int finalGrade;
	private Long studentId;
	private Long courseId;

}
