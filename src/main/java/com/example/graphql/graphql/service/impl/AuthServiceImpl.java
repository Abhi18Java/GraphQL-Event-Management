package com.example.graphql.graphql.service.impl;

import com.example.graphql.graphql.dto.AuthPayloadDTO;
import com.example.graphql.graphql.dto.LoginDTO;
import com.example.graphql.graphql.dto.SignupDTO;
import com.example.graphql.graphql.exceptions.ResourceNotFoundException;
import com.example.graphql.graphql.exceptions.UserAlreadyExistException;
import com.example.graphql.graphql.model.User;
import com.example.graphql.graphql.repository.UserRepository;
import com.example.graphql.graphql.service.AuthService;
import com.example.graphql.graphql.utils.JwtUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Returning token on signup, allowing the user
     * to be logged in right away without needing to make a separate login request
     *
     * @param signupDTO
     * @return
     */

    @Override
    public AuthPayloadDTO signup(SignupDTO signupDTO) {
        userRepository.findByUsername(signupDTO.getUsername())
                .ifPresent(user -> {
                    throw new UserAlreadyExistException("User already present with username: " + signupDTO.getUsername());
                });
        if (userRepository.findByEmail(signupDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("User already present with email: " + signupDTO.getEmail());
        }

        User newUser = modelMapper.map(signupDTO, User.class);
        newUser.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        userRepository.save(newUser);

        String token;
        try {
            token = jwtUtils.generateToken(newUser);
        } catch (BadCredentialsException ex) {
            throw new RuntimeException("Failed to generate token " + ex.getMessage());
        }
        AuthPayloadDTO payloadDTO = new AuthPayloadDTO();
        payloadDTO.setToken(token);
        payloadDTO.setMessage("Signup Successful");
        return payloadDTO;
    }

    @Override
    public AuthPayloadDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid username"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Invalid password");
        }

        String token;
        try {
            token = jwtUtils.generateToken(user);
        } catch (BadCredentialsException ex) {
            throw new RuntimeException("Failed to generate token " + ex.getMessage());
        }
        AuthPayloadDTO payloadDTO = new AuthPayloadDTO();
        payloadDTO.setToken(token);
        payloadDTO.setMessage("Login Successful");
        return payloadDTO;
    }
}
