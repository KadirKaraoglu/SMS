package com.kadirkaraoglu.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class DtoTeacherUI {
	private Long id;
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
    private LocalDate birthDate;
	

}
