package org.incubyte.actors;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class Movie extends Cast {

  private String title;
  private LocalDate releaseDate;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @JsonProperty("release_date")
  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
  }
}
