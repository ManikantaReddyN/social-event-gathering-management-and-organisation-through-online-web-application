package com.secop.event.service;

import com.secop.event.exception.RecordNotFoundException;
import com.secop.event.models.Comment;
import com.secop.event.models.Event;
import com.secop.event.models.Visitor;
import com.secop.event.payload.request.CommentDTO;
import com.secop.event.repository.CommentRepository;
import com.secop.event.repository.EventRepository;
import com.secop.event.repository.VisitorRepository;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CommentService {

  private EventRepository eventRepository;
  private VisitorRepository visitorRepository;
  private CommentRepository commentRepository;

  @Transactional(propagation = Propagation.REQUIRED)
  public Event save(CommentDTO comment, Long userId)
  {
    Visitor participant = visitorRepository.findByUserId(userId);
    Event event = eventRepository.findById(comment.getEventId()).orElseThrow(RecordNotFoundException::new);

    Comment comm = new Comment(comment.getComment(), event, participant);
    comm.setCreatedAt(LocalDateTime.now());
    commentRepository.save(comm);
    return event;
  }
}
