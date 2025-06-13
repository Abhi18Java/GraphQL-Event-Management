package com.example.graphql.graphql.repository;

import com.example.graphql.graphql.model.Booking;
import com.example.graphql.graphql.model.Event;
import com.example.graphql.graphql.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Optional<Booking> findByUserAndEvent(User username, Event event);
    List<Booking> findByUserId(int userId);

    List<Booking> findByEventId(int eventId);
}
