package com.example.graphql.graphql.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    private Integer id;
    private String title;
    private String description;
    private String date;
    private String location;
    private int availableSeats;
    private List<BookingDTO> bookings;

}
