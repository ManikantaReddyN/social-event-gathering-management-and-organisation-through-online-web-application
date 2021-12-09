package com.secop.event.payload.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

  @NotBlank private String comment;
  @NotBlank private Long eventId;
}
