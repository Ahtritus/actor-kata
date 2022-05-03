package org.incubyte.actors;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class Movie {


  String title;
  private String character;
  private String originalLanguage;
  private LocalDate releaseDate;
  private String posterPath;
  private Integer voteCount;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCharacter() {
    return character;
  }

  public void setCharacter(String character) {
    this.character = character;
  }

  @JsonProperty("original_language")
  public String getOriginalLanguage() {
    return originalLanguage;
  }

  public void setOriginalLanguage(String originalLanguage) {
    this.originalLanguage = originalLanguage;
  }

  @JsonProperty("release_date")
  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
  }

  @JsonProperty("poster_path")
  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  @JsonProperty("vote_count")
  public Integer getVoteCount() {
    return voteCount;
  }

  public void setVoteCount(Integer voteCount) {
    this.voteCount = voteCount;
  }
}
