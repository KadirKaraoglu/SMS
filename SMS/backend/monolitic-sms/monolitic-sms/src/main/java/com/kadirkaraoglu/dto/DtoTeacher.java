package com.kadirkaraoglu.dto;

import java.time.LocalDate;
import java.util.Set;

import com.kadirkaraoglu.entities.Course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoTeacher {
	private Long id;
	
	private Set<DtoCourse> courses;
	
    private String personId;
    
    private String name;
  
    private String surname;
  
    private String tcNo;
   
    private LocalDate birthDate;

}
