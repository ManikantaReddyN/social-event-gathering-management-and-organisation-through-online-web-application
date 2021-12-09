package com.secop.event.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

@Entity
@Table(name = "visitor")
@Getter
@Setter
@NoArgsConstructor
public class Visitor extends BaseModel {

  @Column(name = "name")
  private String name;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  User user;

  @OneToMany(mappedBy = "participant", cascade = CascadeType.DETACH)
  private Set<EventParticipant> events = new HashSet<>();

  public Visitor(String name, User user) {
    this.name = name;
    this.user = user;
  }
}
