package com.example.graphql.graphql.config;

import com.example.graphql.graphql.utils.SecuredDirective;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfigs {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer(SecuredDirective securedDirective) {
        return wiringBuilder -> wiringBuilder
                .directive("secured", securedDirective);
    }

}
