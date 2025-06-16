package com.kadirkaraoglu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
	private Long id;
	private String oldpassword;
	private String newPassword;

}
