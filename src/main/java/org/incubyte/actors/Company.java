package org.incubyte.actors;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Company {

  private int id;
  private String logoPath;
  private String name;

  public int getId() {
    return id;
  }

  @JsonProperty("logo_path")
  public String getLogoPath() {
    return logoPath;
  }

  public String getName() {
    return name;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLogoPath(String logoPath) {
    this.logoPath = logoPath;
  }

  public void setName(String name) {
    this.name = name;
  }
}
