package com.kadirkaraoglu.controller.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kadirkaraoglu.controller.IAuthController;
import com.kadirkaraoglu.dto.AuthResponse;
import com.kadirkaraoglu.dto.LoginRequest;
import com.kadirkaraoglu.entities.Person;
import com.kadirkaraoglu.entities.Role;
import com.kadirkaraoglu.entities.RoleType;
import com.kadirkaraoglu.entities.Student;
import com.kadirkaraoglu.exception.BaseException;
import com.kadirkaraoglu.exception.ErrorMessage;
import com.kadirkaraoglu.exception.MessageType;
import com.kadirkaraoglu.repository.StudentRepository;
import com.kadirkaraoglu.security.JwtTokenProvider;
import com.kadirkaraoglu.service.IStudentService;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements IAuthController {
	
	private final IStudentService iStudentService;
	private final JwtTokenProvider jwtTokenProvider;
	private final StudentRepository studentRepository;
	private final AuthenticationManager authenticationManager;

	public AuthController(IStudentService iStudentService, JwtTokenProvider jwtTokenProvider,
			StudentRepository studentRepository, AuthenticationManager authenticationManager) {
		super();
		this.iStudentService = iStudentService;
		this.jwtTokenProvider = jwtTokenProvider;
		this.studentRepository = studentRepository;
		this.authenticationManager = authenticationManager;
	}


	@Override
	@PostMapping(path = "/login-student")
	public ResponseEntity<AuthResponse> loginStudent(@RequestBody LoginRequest loginRequest) throws Exception {
		   Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
	        if (!authentication.isAuthenticated()) {
	          throw new BaseException( new ErrorMessage(MessageType.NO_RECORD_EXIST_STUDENT,"login please") );
	        }
		return  ResponseEntity.ok(new AuthResponse(jwtTokenProvider.generateToken(loginRequest.getUsername()),"Login is succesfull",RoleType.ROLE_STUDENT.getValue())) ;
	}

	@Override
	@PostMapping(path = "/login-teacher")
	public ResponseEntity<AuthResponse> loginTeacher(LoginRequest loginRequest) throws Exception {
		   Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
	        if (!authentication.isAuthenticated()) {
	          throw new BaseException( new ErrorMessage(MessageType.NO_RECORD_EXIST_TEACHER,"login please") );
	        }
		return ResponseEntity.ok(new AuthResponse(jwtTokenProvider.generateToken(loginRequest.getUsername()),"Login is succesfull",RoleType.ROLE_STUDENT.getValue())) ;

	}
	
}
