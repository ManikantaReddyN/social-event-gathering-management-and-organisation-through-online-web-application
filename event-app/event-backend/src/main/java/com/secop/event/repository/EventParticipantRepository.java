package com.secop.event.repository;

import com.secop.event.models.EventParticipant;
import com.secop.event.models.Visitor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long> {

  List<EventParticipant> findAllByEventId(Long eventId);

  EventParticipant findByEventIdAndParticipantId(Long eventId, Long participantId);
}
