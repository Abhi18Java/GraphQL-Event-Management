package com.example.graphql.graphql.service;

import com.example.graphql.graphql.dto.AuthPayloadDTO;
import com.example.graphql.graphql.dto.LoginDTO;
import com.example.graphql.graphql.dto.SignupDTO;

public interface AuthService {

    public AuthPayloadDTO signup(SignupDTO signupDTO);

    public AuthPayloadDTO login(LoginDTO loginDTO);

}
