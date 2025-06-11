package com.example.graphql.graphql.resolver;

import com.example.graphql.graphql.dto.CreateEventDTO;
import com.example.graphql.graphql.model.Event;
import com.example.graphql.graphql.service.EventMutationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
public class EventMutationResolver {

    @Autowired
    private EventMutationService eventMutationService;

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Event createEvent(@Argument("input") CreateEventDTO createEventDTO) {
        return eventMutationService.createEvent(createEventDTO);
    }

}
