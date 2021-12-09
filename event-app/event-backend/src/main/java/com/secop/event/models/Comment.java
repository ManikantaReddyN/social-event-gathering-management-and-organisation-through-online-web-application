package com.secop.event.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends BaseModel {

  @NotBlank
  @Size(max = 512)
  private String text;

  @JsonIgnore
  @OneToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "event_id", referencedColumnName = "id")
  Event event;

  @JsonIgnoreProperties(value = { "events","user" }, allowSetters = true)
  @OneToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "participant_id", referencedColumnName = "id")
  Visitor participant;
}
