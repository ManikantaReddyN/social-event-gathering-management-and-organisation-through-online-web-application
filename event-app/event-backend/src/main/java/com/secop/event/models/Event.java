package com.secop.event.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
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

@Entity
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
public class Event extends BaseModel {

  @Column(name = "name")
  private String name;

  @Column(name = "location")
  private String location;

  @Column(name = "description")
  private String description;

  @Column(name = "time")
  private LocalDateTime time;

  @JsonIgnoreProperties(value = { "events" }, allowSetters = true)
  @OneToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "organizer_id", referencedColumnName = "id")
  Visitor organizer;

  @JsonIgnore
  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
  private Set<EventParticipant> participants = new HashSet<>();

  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
  private Set<Comment> comment = new HashSet<>();

  public Event(String name, String location, String description, LocalDateTime time,
      Visitor organizer) {
    this.name = name;
    this.location = location;
    this.description = description;
    this.time = time;
    this.organizer = organizer;
  }
}
