package com.secop.event.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event_participant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventParticipant extends BaseModel {

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "event_id")
  private Event event;

  @JsonIgnoreProperties(value = { "events" }, allowSetters = true)
  @OneToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "participant_id", referencedColumnName = "id")
  Visitor participant;

  private Boolean approved;

  private Boolean joining = false;

}
