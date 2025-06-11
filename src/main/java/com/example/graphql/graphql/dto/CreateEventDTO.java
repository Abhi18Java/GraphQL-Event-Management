package com.example.graphql.graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventDTO {

    private String title;
    private String description;
    private String date;
    private String location;
    private int availableSeats;
}
