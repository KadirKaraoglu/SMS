package com.kadirkaraoglu.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class DtoStudentUI {
	@NotBlank(message = "Please fill the blanks")
	@NotEmpty(message = "Please fill the blanks")
	@Size(min = 2 ,  message = "Name must be longer than 1 character")
	private String name;
	@Size(min = 2 , message = "Surname must be longer than 1 character")
	@NotBlank(message = "Please fill the blanks")
	@NotEmpty(message = "Please fill the blanks")
    private String surname;
	@Size(min = 11 , max = 11 , message = "TCKN must be 11 number")
	@NotBlank(message = "Please fill the blanks")
	@NotEmpty(message = "Please fill the blanks")
    private String tcNo;
	@JsonFormat(pattern = "MM/dd/yyyy")
	@NotBlank(message = "Please fill the blanks")
	@NotEmpty(message = "Please fill the blanks")
    private LocalDate birthDate;
	@NotBlank(message = "Please fill the blanks")
	@NotEmpty(message = "Please fill the blanks")
	private Long facultyId; 


}
