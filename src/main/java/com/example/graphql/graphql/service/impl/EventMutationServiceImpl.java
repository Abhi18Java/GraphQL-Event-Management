package com.example.graphql.graphql.service.impl;

import com.example.graphql.graphql.dto.CreateEventDTO;
import com.example.graphql.graphql.model.Event;
import com.example.graphql.graphql.repository.EventRepository;
import com.example.graphql.graphql.service.EventMutationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventMutationServiceImpl implements EventMutationService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Event createEvent(CreateEventDTO createEventDTO) {
        try {
            Event event = modelMapper.map(createEventDTO, Event.class);
            return eventRepository.save(event);
        } catch (Exception e) {
            System.out.println("Error while creating event " + e.getMessage());
            throw new RuntimeException("Error occurred while creating event.");
        }
    }
}
