package com.kadirkaraoglu.dto;

import java.util.Set;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StudentCourseRequest {
	@Size(min = 1)
	private Set<Long> courseIds;

	

}
