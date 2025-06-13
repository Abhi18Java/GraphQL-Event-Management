package com.example.graphql.graphql.resolver;

import com.example.graphql.graphql.dto.BookingDTO;
import com.example.graphql.graphql.dto.CreateEventDTO;
import com.example.graphql.graphql.dto.EventDTO;
import com.example.graphql.graphql.exceptions.UnauthorizedAccessException;
import com.example.graphql.graphql.model.Booking;
import com.example.graphql.graphql.model.Event;
import com.example.graphql.graphql.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Controller
public class EventResolver {

    @Autowired
    private EventService eventService;

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public EventDTO createEvent(@Argument("input") CreateEventDTO createEventDTO) {
        return eventService.createEvent(createEventDTO);
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public EventDTO updateEvent(@Argument int eventId, @Argument("input") CreateEventDTO createEventDTO) {
        return eventService.updateEvent(eventId, createEventDTO);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public BookingDTO bookEvent(@Argument int eventId, @Argument int seats) {
        return eventService.bookEvent(eventId, seats);
    }

    @MutationMapping
    @PreAuthorize("hasRole('USER')")
    public Boolean cancelBooking(@Argument int bookingId) {
        return eventService.cancelBooking(bookingId);
    }

    @QueryMapping
    public List<EventDTO> getAllEvents() throws AccessDeniedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new UnauthorizedAccessException("Unauthorized");
        }

        return eventService.getAllEvents();
    }

    @QueryMapping
    public EventDTO getEventById(@Argument int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new UnauthorizedAccessException("Unauthorized");
        }
        return eventService.getEventById(id);
    }

    @QueryMapping
    @PreAuthorize("hasRole('USER')")
    public List<BookingDTO> getUserBookings(@Argument int userId) {
        return eventService.getUserBookings(userId);
    }

    @QueryMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<BookingDTO> getEventBookings(@Argument int eventId) {
        return eventService.getEventBookings(eventId);
    }

}
