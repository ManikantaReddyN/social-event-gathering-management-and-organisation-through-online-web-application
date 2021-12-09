package com.secop.event.service;

import com.secop.event.exception.RecordNotFoundException;
import com.secop.event.models.Event;
import com.secop.event.models.EventParticipant;
import com.secop.event.models.Visitor;
import com.secop.event.repository.EventParticipantRepository;
import com.secop.event.repository.EventRepository;
import com.secop.event.repository.VisitorRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventParticipantService {

  private EventRepository eventRepository;
  private VisitorRepository visitorRepository;
  private EventParticipantRepository eventParticipantRepository;

  public EventParticipant get(Long id) {
    return eventParticipantRepository.findById(id).orElseThrow(RecordNotFoundException::new);
  }

  public void delete(Long id) {
    EventParticipant cl = eventParticipantRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    eventParticipantRepository.delete(cl);
  }

  public List<EventParticipant> findParticipants(Long eventId) {
    return eventParticipantRepository.findAllByEventId(eventId);
  }

  public EventParticipant save(Long eventId, Long userId) {
    Event event = eventRepository.findById(eventId).orElseThrow(RecordNotFoundException::new);
    Visitor visitor = visitorRepository.findByUserId(userId);
    for (EventParticipant ev: visitor.getEvents()) {
      if(ev.getEvent().getTime().isEqual(event.getTime())){
        throw new RuntimeException("You already have event at that time");
      }
    }

    EventParticipant ep = new EventParticipant(event, visitor, false, false);
    ep.setCreatedAt(LocalDateTime.now());
    eventParticipantRepository.save(ep);
    return ep;
  }

  public EventParticipant approveRequest(Long eventRequestId) {
    EventParticipant ep =
        eventParticipantRepository
            .findById(eventRequestId)
            .orElseThrow(RecordNotFoundException::new);
    ep.setApproved(true);
    ep.setUpdatedAt(LocalDateTime.now());
    eventParticipantRepository.save(ep);
    return ep;
  }

  @Transactional
  public Event updateOrganizer(Long eventId, Long participantId) {
    Event event = eventRepository.findById(eventId).orElseThrow(RecordNotFoundException::new);
    EventParticipant participant = eventParticipantRepository.findById(participantId)
        .orElseThrow(RecordNotFoundException::new);
    event.setOrganizer(participant.getParticipant());
    eventRepository.save(event);
    eventParticipantRepository.delete(participant);
    return  event;
  }

  public EventParticipant joining(Long eventId, Long loggedInUserId) {
    Visitor visitor = visitorRepository.findByUserId(loggedInUserId);
    EventParticipant evpart = eventParticipantRepository
        .findByEventIdAndParticipantId(eventId, visitor.getId());
    evpart.setJoining(true);
    eventParticipantRepository.save(evpart);
    return evpart;
  }
}
