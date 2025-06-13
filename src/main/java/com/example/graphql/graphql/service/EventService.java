package com.example.graphql.graphql.service;

import com.example.graphql.graphql.dto.BookingDTO;
import com.example.graphql.graphql.dto.CreateEventDTO;
import com.example.graphql.graphql.dto.EventDTO;
import com.example.graphql.graphql.model.Booking;
import com.example.graphql.graphql.model.Event;

import java.util.List;

public interface EventService {
    EventDTO createEvent(CreateEventDTO createEventDTO);
    EventDTO updateEvent(int eventId, CreateEventDTO createEventDTO);
    BookingDTO bookEvent(int eventId, int seats);

    Boolean cancelBooking(int bookingId);

    List<EventDTO> getAllEvents();

    EventDTO getEventById(int id);

    List<BookingDTO> getUserBookings(int userId);

    List<BookingDTO> getEventBookings(int eventId);
}
