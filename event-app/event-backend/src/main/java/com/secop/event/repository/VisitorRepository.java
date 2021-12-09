package com.secop.event.repository;

import com.secop.event.models.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

  Visitor findByUserId(Long userId);
}
