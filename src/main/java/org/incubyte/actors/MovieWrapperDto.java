package org.incubyte.actors;

import java.util.List;

public class MovieWrapperDto {

  private List<Movie> cast;

  public List<Movie> getCast() {
    return cast;
  }

  public void setCast(List<Movie> movies) {
    this.cast = movies;
  }
}
