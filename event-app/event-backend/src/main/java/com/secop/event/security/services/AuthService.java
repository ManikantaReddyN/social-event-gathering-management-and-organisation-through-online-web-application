package com.secop.event.security.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  public UserDetailsImpl getLoggedInUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return ((UserDetailsImpl) auth.getPrincipal());
  }

  public Long getLoggedInUserId() {
    return getLoggedInUser().getId();
  }
}
