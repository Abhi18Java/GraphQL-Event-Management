package com.example.graphql.graphql.service;

import com.example.graphql.graphql.dto.CreateEventDTO;
import com.example.graphql.graphql.model.Booking;
import com.example.graphql.graphql.model.Event;

public interface EventService {
    Event createEvent(CreateEventDTO createEventDTO);
    Event updateEvent(int eventId, CreateEventDTO createEventDTO);
    Booking bookEvent(int eventId, int seats);

    Boolean cancelBooking(int bookingId);
}
