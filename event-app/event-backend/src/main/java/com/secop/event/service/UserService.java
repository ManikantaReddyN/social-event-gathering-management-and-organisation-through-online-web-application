package com.secop.event.service;

import com.secop.event.constants.ERole;
import com.secop.event.models.Role;
import com.secop.event.models.User;
import com.secop.event.models.Visitor;
import com.secop.event.repository.RoleRepository;
import com.secop.event.repository.UserRepository;
import com.secop.event.repository.VisitorRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired UserRepository userRepository;
  @Autowired RoleRepository roleRepository;
  @Autowired VisitorRepository visitorRepository;


  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Transactional
  public void save(User user, Visitor visitor) {

    user.setCreatedAt(LocalDateTime.now());
    visitor.setCreatedAt(LocalDateTime.now());

    userRepository.save(user);
    visitorRepository.save(visitor);
  }

  public Optional<Role> findByName(ERole eRole) {
   return  roleRepository.findByName(eRole);
  }
}
