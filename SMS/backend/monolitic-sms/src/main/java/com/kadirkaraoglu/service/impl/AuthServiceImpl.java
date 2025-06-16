package com.kadirkaraoglu.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kadirkaraoglu.dto.AuthResponse;
import com.kadirkaraoglu.dto.ChangePasswordRequest;
import com.kadirkaraoglu.dto.LoginRequest;
import com.kadirkaraoglu.entities.Person;
import com.kadirkaraoglu.entities.RoleType;
import com.kadirkaraoglu.exception.BaseException;
import com.kadirkaraoglu.exception.ErrorMessage;
import com.kadirkaraoglu.exception.MessageType;
import com.kadirkaraoglu.repository.PersonRepository;
import com.kadirkaraoglu.security.JwtTokenProvider;
import com.kadirkaraoglu.service.AuthService;
import com.kadirkaraoglu.service.IStudentService;
@Service
public class AuthServiceImpl implements AuthService {
	private final JwtTokenProvider jwtTokenProvider;
	private final AuthenticationManager authenticationManager;
	private final PersonRepository personRepository;
	private final BCryptPasswordEncoder passwordEncoder;;

	public AuthServiceImpl(JwtTokenProvider jwtTokenProvider,
			 AuthenticationManager authenticationManager,
			IStudentService studentService,PersonRepository personRepository
			, BCryptPasswordEncoder passwordEncoder) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.authenticationManager = authenticationManager;
		this.personRepository = personRepository;
		this.passwordEncoder = passwordEncoder;
	}
	@Override
	public AuthResponse authenticateStudent(LoginRequest loginRequest)  {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
			Person person = personRepository.findPersonByUsername(loginRequest.getUsername());
			if (!(person.getRole().getValue().equals(RoleType.ROLE_STUDENT.getValue()))) {
				throw new BaseException(new ErrorMessage( MessageType.LOGIN_ERROR_STUDENT,null));
			}
			if (authentication.isAuthenticated()) {
				if (true == isFirstLogin(loginRequest.getUsername() )) {
					throw new BaseException(new ErrorMessage(MessageType.CHANGE_PASSWORD,null));
				}
			}
			
	        return  new AuthResponse(jwtTokenProvider.generateToken(loginRequest.getUsername()),"Login is succesfull",RoleType.ROLE_STUDENT.getValue()) ;
		} catch (BadCredentialsException e) {
			throw new BaseException(new ErrorMessage( MessageType.PASSWORD_WRONG, loginRequest.getPassword().toString()));
		}
		   
		
	}

	@Override
	public AuthResponse authenticateTeacher(LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			   if (authentication.isAuthenticated()) {
					if (true == isFirstLogin(loginRequest.getUsername() )) {
						throw new BaseException(new ErrorMessage(MessageType.CHANGE_PASSWORD,null));
					}
				}
			   Person person = personRepository.findPersonByUsername(loginRequest.getUsername());
				if (!(person.getRole().getValue().equals(RoleType.ROLE_TEACHER.getValue()))) {
					throw new BaseException(new ErrorMessage( MessageType.LOGIN_ERROR_TEACHER,null));
				}
			return new AuthResponse(jwtTokenProvider.generateToken(loginRequest.getUsername()),"Login is succesfull",RoleType.ROLE_TEACHER.getValue()) ;
		} catch (BadCredentialsException e) {
			throw new BaseException(new ErrorMessage( MessageType.PASSWORD_WRONG,null));
		}
		}

	@Override
	public AuthResponse authenticateAdmin(LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			 Person person = personRepository.findPersonByUsername(loginRequest.getUsername());
				if (!(person.getRole().getValue().equals(RoleType.ROLE_ADMIN.getValue()))) {
					throw new BaseException(new ErrorMessage( MessageType.LOGIN_ERROR_ADNIN,null));
				}   
			if (authentication.isAuthenticated()) {
					if (true == isFirstLogin(loginRequest.getUsername() )) {
						throw new BaseException(new ErrorMessage(MessageType.CHANGE_PASSWORD,null));
					}
				}
			  
			return new AuthResponse(jwtTokenProvider.generateToken(loginRequest.getUsername()),"Login is succesfull",RoleType.ROLE_ADMIN.getValue()) ;
		} catch (BadCredentialsException e) {
			throw new BaseException(new ErrorMessage( MessageType.PASSWORD_WRONG,null));
		}
		}
	@Override
	public boolean changePassword(ChangePasswordRequest changePasswordRequest) throws Exception {
		Person person = personRepository.findPersonByUsername(changePasswordRequest.getUsername());
		if (person==null) {
			throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.NO_RECORD_EXIST_STUDENT, changePasswordRequest.toString())) ;
		}
		if (!(passwordEncoder.matches(changePasswordRequest.getOldPassword(), person.getPassword()))) {
		    throw new BaseException(new ErrorMessage(com.kadirkaraoglu.exception.MessageType.OLD_PASSWORD_IS_WRONG, changePasswordRequest.toString())) ;
		}
	
		person.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
		person.setFirstLogin(false);
		personRepository.save(person);
		return true;
	}
	@Override
	public boolean isFirstLogin(String username) {
		Person person=  personRepository.findPersonByUsername(username);
		return person.isFirstLogin();
	}
}