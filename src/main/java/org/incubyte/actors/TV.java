package org.incubyte.actors;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class TV extends Cast {

  private String originalName;
  private LocalDate firstAirDate;

  @JsonProperty("original_name")
  public String getOriginalName() {
    return originalName;
  }

  @JsonProperty("first_air_date")
  public LocalDate getFirstAirDate() {
    return firstAirDate;
  }
}
