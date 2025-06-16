package com.kadirkaraoglu.service;

import org.springframework.stereotype.Service;

import com.kadirkaraoglu.dto.AuthResponse;
import com.kadirkaraoglu.dto.ChangePasswordRequest;
import com.kadirkaraoglu.dto.LoginRequest;
@Service
public interface AuthService {
    AuthResponse authenticateStudent(LoginRequest loginRequest);
    AuthResponse authenticateTeacher(LoginRequest loginRequest);
    AuthResponse authenticateAdmin(LoginRequest loginRequest);
    public boolean changePassword(ChangePasswordRequest changePasswordRequest)throws Exception;
    public boolean isFirstLogin (String personId);
}