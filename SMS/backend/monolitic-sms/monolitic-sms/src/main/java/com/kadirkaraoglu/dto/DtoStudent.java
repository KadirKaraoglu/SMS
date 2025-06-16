package com.kadirkaraoglu.dto;

import java.time.LocalDate;
import java.util.Set;

import com.kadirkaraoglu.entities.Course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DtoStudent {
	
	private Set<Course> courses;
	private Long id;
    private String username;
   
    private String name;
   
    private String surname;
   
    private String tcNo;
   
    private LocalDate birthDate;
    
    private DtoFaculty dtoFaculty;

}
