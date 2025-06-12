package com.example.graphql.graphql.resolver;

import com.example.graphql.graphql.dto.CreateEventDTO;
import com.example.graphql.graphql.model.Booking;
import com.example.graphql.graphql.model.Event;
import com.example.graphql.graphql.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class EventResolver {

    @Autowired
    private EventService eventService;

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Event createEvent(@Argument("input") CreateEventDTO createEventDTO) {
        return eventService.createEvent(createEventDTO);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Event updateEvent(@Argument int eventId, @Argument("input") CreateEventDTO createEventDTO) {
        return eventService.updateEvent(eventId, createEventDTO);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public Booking bookEvent(@Argument int eventId, @Argument int seats) {
        return eventService.bookEvent(eventId, seats);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public Boolean cancelBooking(@Argument int bookingId) {
        return eventService.cancelBooking(bookingId);
    }

}
