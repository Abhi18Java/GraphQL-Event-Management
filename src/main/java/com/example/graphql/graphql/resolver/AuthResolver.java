package com.example.graphql.graphql.resolver;

import com.example.graphql.graphql.dto.AuthPayloadDTO;
import com.example.graphql.graphql.dto.LoginDTO;
import com.example.graphql.graphql.dto.SignupDTO;
import com.example.graphql.graphql.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AuthResolver {

    @Autowired
    private AuthService authService;

    @MutationMapping
    public AuthPayloadDTO signup(@Argument("input") SignupDTO signupDTO) {
        return authService.signup(signupDTO);
    }

    @MutationMapping
    public AuthPayloadDTO login(@Argument("input") LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

}
