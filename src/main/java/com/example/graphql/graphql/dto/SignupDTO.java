package com.example.graphql.graphql.dto;

import com.example.graphql.graphql.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDTO {

    private String username;
    private String email;
    private String password;
    private Role role;

}
