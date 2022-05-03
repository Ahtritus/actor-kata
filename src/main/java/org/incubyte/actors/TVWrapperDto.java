package org.incubyte.actors;

import java.util.List;

public class TVWrapperDto {
  private List<TV> cast;

  public List<TV> getCast() {
    return cast;
  }

  public void setCast(List<TV> cast) {
    this.cast = cast;
  }
}
