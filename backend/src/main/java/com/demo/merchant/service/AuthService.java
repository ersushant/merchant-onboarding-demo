package com.demo.merchant.service;

import com.demo.merchant.dto.auth.LoginRequest;
import com.demo.merchant.dto.auth.LoginResponse;
import com.demo.merchant.dto.auth.RegisterRequest;

public interface AuthService {

    String register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}