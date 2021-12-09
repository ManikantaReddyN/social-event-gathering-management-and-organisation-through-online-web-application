package com.secop.event.service;

import com.secop.event.exception.RecordNotFoundException;
import com.secop.event.models.Event;
import com.secop.event.models.Visitor;
import com.secop.event.payload.request.EventDTO;
import com.secop.event.repository.EventRepository;
import com.secop.event.repository.VisitorRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@AllArgsConstructor
public class EventService {

  private EventRepository eventRepository;
  private VisitorRepository visitorRepository;

  @Transactional(propagation = Propagation.REQUIRED)
  public Event save(EventDTO createEventRequest, Long userId) {

    Visitor organizer = visitorRepository.findByUserId(userId);

    Event event =
        new Event(
            createEventRequest.getName(),
            createEventRequest.getLocation(),
            createEventRequest.getDescription(),
            createEventRequest.getTime(),
            organizer);
    event.setCreatedAt(LocalDateTime.now());
    eventRepository.save(event);
    return event;
  }

  public Event get(Long id) {
    return eventRepository.findById(id).orElseThrow(RecordNotFoundException::new);
  }

  public void delete(Long id) {
    Event cl = eventRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    eventRepository.delete(cl);
  }

  public List<Event> list(String searchString) {
    if (StringUtils.isEmpty(searchString)) return eventRepository.findAll();

    else{
      String str = "%"+searchString+"%";
      return eventRepository.findAllByNameLikeOrLocationLikeOrDescriptionLike(str, str, str);
    }
  }

  public Event update(Long id, EventDTO event) {
    Event cl = eventRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    cl.setName(event.getName());
    cl.setDescription(event.getDescription());
    cl.setTime(event.getTime());

    if (event.getOrganizerId() != null && event.getOrganizerId() > 0) {
      Visitor visitor = visitorRepository.findByUserId(event.getOrganizerId());
      cl.setOrganizer(visitor);
    }
    eventRepository.save(cl);
    return cl;
  }
}
