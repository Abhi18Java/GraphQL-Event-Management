package com.example.graphql.graphql.repository;

import com.example.graphql.graphql.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
