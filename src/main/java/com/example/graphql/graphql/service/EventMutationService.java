package com.example.graphql.graphql.service;

import com.example.graphql.graphql.dto.CreateEventDTO;
import com.example.graphql.graphql.model.Event;

public interface EventMutationService {
    Event createEvent(CreateEventDTO createEventDTO);
}
