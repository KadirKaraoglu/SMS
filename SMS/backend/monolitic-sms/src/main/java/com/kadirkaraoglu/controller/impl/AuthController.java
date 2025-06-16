package com.kadirkaraoglu.controller.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.controller.IAuthController;
import com.kadirkaraoglu.dto.AuthResponse;
import com.kadirkaraoglu.dto.ChangePasswordRequest;
import com.kadirkaraoglu.dto.LoginRequest;
import com.kadirkaraoglu.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements IAuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {

		this.authService = authService;
	}

	@Override
	@PostMapping(path = "/login-student")
	public ResponseEntity<AuthResponse> loginStudent(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
		return ResponseEntity.ok(authService.authenticateStudent(loginRequest));

	}

	@Override
	@PostMapping(path = "/login-admin")
	public ResponseEntity<AuthResponse> loginAdmin(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
		return ResponseEntity.ok(authService.authenticateAdmin(loginRequest));

	}

	@Override
	@PostMapping(path = "/login-academician")
	public ResponseEntity<AuthResponse> loginTeacher(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
		return ResponseEntity.ok(authService.authenticateTeacher(loginRequest));

	}

		@Override
		@PostMapping("/change-password")
		public boolean changePassword(@RequestBody @Valid ChangePasswordRequest changePasswordRequest) throws Exception {
			return authService.changePassword(changePasswordRequest);
	
		}

}
