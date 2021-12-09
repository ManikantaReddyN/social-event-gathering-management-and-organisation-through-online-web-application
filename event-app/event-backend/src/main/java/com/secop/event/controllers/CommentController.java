package com.secop.event.controllers;

import com.secop.event.models.Event;
import com.secop.event.payload.request.CommentDTO;
import com.secop.event.security.services.AuthService;
import com.secop.event.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {

  private CommentService commentService;
  private AuthService authService;

  @PostMapping()
  @PreAuthorize("hasRole('VISITOR')")
  public ResponseEntity<Event> save(@RequestBody CommentDTO commentDTO) {
    Event cl = commentService.save(commentDTO, authService.getLoggedInUserId());
    return new ResponseEntity<>(cl, HttpStatus.CREATED);
  }
}
