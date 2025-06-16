package com.kadirkaraoglu.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class StudentCourseRequest {
	
	private Long studentId;
	private Set<Long> courseIds;

}
