package com.kadirkaraoglu.dto;

import javax.validation.constraints.Size;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoGradeUI {
	@Size(min = 0 , max = 100 , message = "Grade must be between 0-100")
	private int score ;
	private Long studentId;
	private Long courseId;

}
