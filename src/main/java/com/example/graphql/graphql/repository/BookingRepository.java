package com.example.graphql.graphql.repository;

import com.example.graphql.graphql.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
