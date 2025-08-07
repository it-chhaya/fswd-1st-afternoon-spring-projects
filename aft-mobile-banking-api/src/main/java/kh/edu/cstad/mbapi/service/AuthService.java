package kh.edu.cstad.mbapi.service;

import kh.edu.cstad.mbapi.dto.RegisterRequest;
import kh.edu.cstad.mbapi.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest registerRequest);

    void verifyEmail(String userId);

}
