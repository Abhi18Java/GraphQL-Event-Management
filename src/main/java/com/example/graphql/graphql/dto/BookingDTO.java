package com.example.graphql.graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    private Integer id;
    private EventDTO event;
    private UserDTO user;
    private String timestamp;
    private int seats;
}
