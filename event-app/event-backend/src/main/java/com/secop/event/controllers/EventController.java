package com.secop.event.controllers;

import com.secop.event.models.Event;
import com.secop.event.models.EventParticipant;
import com.secop.event.payload.request.EventDTO;
import com.secop.event.security.services.AuthService;
import com.secop.event.service.EventParticipantService;
import com.secop.event.service.EventService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/event")
@AllArgsConstructor
public class EventController {

  private EventService eventService;
  private AuthService authService;
  private EventParticipantService eventParticipantService;

  @GetMapping(value = "/{id}")
  @PreAuthorize("hasRole('VISITOR')")
  public ResponseEntity<Event> get(@PathVariable("id") Long id) {
    Event client = eventService.get(id);
    return new ResponseEntity<>(client, HttpStatus.OK);
  }

  @PostMapping()
  @PreAuthorize("hasRole('VISITOR')")
  public ResponseEntity<Event> save(@RequestBody EventDTO createEventRequest) {
    Event cl = eventService.save(createEventRequest, authService.getLoggedInUserId());
    return new ResponseEntity<>(cl, HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{id}")
  @PreAuthorize("hasRole('VISITOR')")
  public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    eventService.delete(id);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @PutMapping(value = "/{id}")
  @PreAuthorize("hasRole('VISITOR')")
  public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody EventDTO eventDTO) {
    eventService.update(id, eventDTO);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @GetMapping()
  @PreAuthorize("hasRole('VISITOR')")
  public ResponseEntity<List<Event>> list(@RequestParam(value ="searchString", required = false) String searchString ) {
    List<Event> list = eventService.list(searchString);
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping(value = "/request/{eventId}")
  @PreAuthorize("hasRole('VISITOR')")
  public ResponseEntity<EventParticipant> request(@PathVariable("eventId") Long eventId) {
    EventParticipant ep = eventParticipantService.save(eventId, authService.getLoggedInUserId());
    return new ResponseEntity<>(ep, HttpStatus.OK);
  }

  @PostMapping(value = "/approve/{requestId}")
  @PreAuthorize("hasRole('VISITOR')")
  public ResponseEntity<EventParticipant> approveRequest(@PathVariable("requestId") Long requestId) {
    EventParticipant ep = eventParticipantService.approveRequest(requestId);
    return new ResponseEntity<>(ep, HttpStatus.OK);
  }

  @PostMapping(value = "/joining/{eventId}")
  @PreAuthorize("hasRole('VISITOR')")
  public ResponseEntity<EventParticipant> joining(@PathVariable("eventId") Long eventId) {
    EventParticipant ep = eventParticipantService.joining(eventId, authService.getLoggedInUserId());
    return new ResponseEntity<>(ep, HttpStatus.OK);
  }


  @GetMapping(value = "/participant/{eventId}")
  @PreAuthorize("hasRole('VISITOR')")
  public ResponseEntity<List<EventParticipant>> getParticipants(@PathVariable("eventId") Long eventId) {
    List<EventParticipant> ep = eventParticipantService.findParticipants(eventId);
    return new ResponseEntity<>(ep, HttpStatus.OK);
  }


  @GetMapping(value = "/organizer/{eventId}/{visitorId}")
  @PreAuthorize("hasRole('VISITOR')")
  public ResponseEntity<Event> updateOrganizer(@PathVariable("eventId") Long eventId, @PathVariable("visitorId") Long visitorId) {
    Event ep = eventParticipantService.updateOrganizer(eventId, visitorId);
    return new ResponseEntity<>(ep, HttpStatus.OK);
  }

}
