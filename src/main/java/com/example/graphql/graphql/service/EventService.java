package com.example.graphql.graphql.service;

import com.example.graphql.graphql.dto.CreateEventDTO;
import com.example.graphql.graphql.model.Booking;
import com.example.graphql.graphql.model.Event;

import java.util.List;

public interface EventService {
    Event createEvent(CreateEventDTO createEventDTO);
    Event updateEvent(int eventId, CreateEventDTO createEventDTO);
    Booking bookEvent(int eventId, int seats);

    Boolean cancelBooking(int bookingId);

    List<Event> getAllEvents();

    Event getEventById(int id);

    List<Booking> getUserBookings(int userId);

    List<Booking> getEventBookings(int eventId);
}
