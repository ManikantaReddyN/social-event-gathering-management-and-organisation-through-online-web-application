package com.secop.event.repository;

import com.secop.event.models.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
  List<Event> findAllByOrganizerId(Long organizerId);

  List<Event> findAllByNameLikeOrLocationLikeOrDescriptionLike(String searchString, String searchString1, String searchString2);
}
