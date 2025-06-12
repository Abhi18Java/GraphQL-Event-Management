package com.example.graphql.graphql.service.impl;

import com.example.graphql.graphql.dto.CreateEventDTO;
import com.example.graphql.graphql.exceptions.IllegalArgumentException;
import com.example.graphql.graphql.exceptions.ResourceNotFoundException;
import com.example.graphql.graphql.exceptions.UnauthorizedAccessException;
import com.example.graphql.graphql.model.Booking;
import com.example.graphql.graphql.model.Event;
import com.example.graphql.graphql.model.User;
import com.example.graphql.graphql.repository.BookingRepository;
import com.example.graphql.graphql.repository.EventRepository;
import com.example.graphql.graphql.repository.UserRepository;
import com.example.graphql.graphql.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Event createEvent(CreateEventDTO createEventDTO) {
        if (createEventDTO.getTitle() == null || createEventDTO.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title field cannot be null");
        }
        LocalDate eventDate;
        try {
            eventDate = LocalDate.parse(createEventDTO.getDate());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd");
        }
        if (eventDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Event date cannot be in the past");
        }
        if (createEventDTO.getAvailableSeats() <= 0) {
            throw new IllegalArgumentException("No Seats?");
        }
        if (createEventDTO.getTitle() == null || createEventDTO.getTitle().trim().isEmpty()) {
            throw new java.lang.IllegalArgumentException("Title is required.");
        }
        if (createEventDTO.getLocation() == null || createEventDTO.getLocation().trim().isEmpty()) {
            throw new java.lang.IllegalArgumentException("Location is required.");
        }

        Event event = modelMapper.map(createEventDTO, Event.class);
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(int eventId, CreateEventDTO createEventDTO) {

        Event existingEvent = eventRepository.findById(eventId).orElseThrow(
                () -> new ResourceNotFoundException("Event not found with given ID:" + eventId));

        LocalDate eventDate;
        try {
            eventDate = LocalDate.parse(createEventDTO.getDate());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd");
        }

        if (eventDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Event date cannot be in the past");
        }

        // 3. Other field validations
        if (createEventDTO.getAvailableSeats() <= 0) {
            throw new IllegalArgumentException("Available seats must be greater than 0");
        }

        if (createEventDTO.getTitle() == null || createEventDTO.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }

        if (createEventDTO.getLocation() == null || createEventDTO.getLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Location is required");
        }

        existingEvent.setTitle(createEventDTO.getTitle());
        existingEvent.setDescription(createEventDTO.getDescription());
        existingEvent.setDate(eventDate.toString());  //Check the casting
        existingEvent.setLocation(createEventDTO.getLocation());
        existingEvent.setAvailableSeats(createEventDTO.getAvailableSeats());

        return eventRepository.save(existingEvent);

    }

    @Override
    @Transactional
    public Booking bookEvent(int eventId, int seats) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("No Event found with eventId: " + eventId));

        Optional<Booking> existingBooking = bookingRepository.findByUserAndEvent(user, event);
        if (existingBooking.isPresent()) {
            throw new IllegalStateException("User has already booked this event.");
        }

        if (event.getAvailableSeats() <= seats) {
            throw new IllegalArgumentException("No seats available for this event");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setEvent(event);
        booking.setTimestamp(LocalDateTime.now().toString());
        booking.setSeats(seats);

        event.setAvailableSeats(event.getAvailableSeats() - seats);
        eventRepository.save(event);
        return bookingRepository.save(booking);
    }

    @Override
    public Boolean cancelBooking(int bookingId) {

        Booking existingBooking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("No Booking found for ID: " + bookingId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if (!currentUser.getId().equals(existingBooking.getUser().getId())) {
            throw new UnauthorizedAccessException("You are not authorized to cancel this booking");
        }

        Event event = existingBooking.getEvent();
        event.setAvailableSeats(event.getAvailableSeats() + existingBooking.getSeats());
        eventRepository.save(event);

        bookingRepository.delete(existingBooking);
        return true;
    }

}

