package org.incubyte.actors;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

@MicronautTest
public class PersonEndPointTest {
  @Client("/")
  @Inject
  HttpClient httpClient;

  @Test
  public void should_search_for_people_based_on_query() {
    List<SearchResult> results =
        httpClient
            .toBlocking()
            .retrieve(
                HttpRequest.GET("people?query=tom+cruise"), Argument.listOf(SearchResult.class));
    assertThat(results.size()).isGreaterThan(0);
    SearchResult result = results.get(0);
    assertThat(result.getName()).isNotEmpty();
    assertThat(result.getProfilePath()).isNotEmpty();
    assertThat(result.getId()).isEqualTo(500L);
  }

  @Test()
  public void should_return_404_if_person_not_found() {
    HttpClientResponseException httpClientResponseException =
        assertThrows(
            HttpClientResponseException.class,
            () -> {
              List<SearchResult> results =
                  httpClient
                      .toBlocking()
                      .retrieve(
                          HttpRequest.GET("people?query=ABC+XYZ"),
                          Argument.listOf(SearchResult.class));
            });
    assertThat(httpClientResponseException.getMessage()).isEqualTo("Not Found");
    assertThat(httpClientResponseException.getResponse().code())
        .isEqualTo(HttpResponse.notFound().status().getCode());
  }

  @Test
  public void should_return_person_info() {

    Person person =
        httpClient.toBlocking().retrieve(HttpRequest.GET("people/500"), Argument.of(Person.class));
    assertThat(person.getName()).isEqualTo("Tom Cruise");
    assertThat(person.getImage()).isNotEmpty();
    assertThat(person.getGender()).isEqualTo(2);
    assertThat(person.getPlaceOfBirth()).isEqualTo("Syracuse, New York, USA");
  }

  @Test
  void should_return_list_of_movies_for_person() {
    List<Movie> movies =
        httpClient
            .toBlocking()
            .retrieve(HttpRequest.GET("people/500/movies"), Argument.listOf(Movie.class));

    assertThat(movies).isNotEmpty();
    Movie movieResult = movies.get(0);
    assertThat(movieResult).isNotNull();
    assertThat(movieResult.getTitle()).isEqualTo("War of the Worlds");
    assertThat(movieResult.getCharacter()).isEqualTo("Ray Ferrier");
    assertThat(movieResult.getOriginalLanguage()).isEqualTo("en");
    assertThat(movieResult.getReleaseDate()).isEqualTo(LocalDate.of(2005, 6, 28));
    assertThat(movieResult.getPosterPath()).isEqualTo("/6Biy7R9LfumYshur3YKhpj56MpB.jpg");
    assertThat(movieResult.getVoteCount()).isEqualTo(6843);
  }

  @Test
  void should_return_list_of_tv_shows_for_person() {
    List<TV> tvShows =
        httpClient
            .toBlocking()
            .retrieve(HttpRequest.GET("people/500/tv"), Argument.listOf(TV.class));

    assertThat(tvShows).isNotEmpty();
    TV tvResult = tvShows.get(0);
    assertThat(tvResult).isNotNull();
    assertThat(tvResult.getOriginalName()).isEqualTo("The One Show");
    assertThat(tvResult.getCharacter()).isEqualTo("Self");
    assertThat(tvResult.getOriginalLanguage()).isEqualTo("en");
    assertThat(tvResult.getFirstAirDate()).isEqualTo(LocalDate.parse("2006-08-14"));
    assertThat(tvResult.getPosterPath()).isEqualTo("/qCOlMsNSMDF5ZSvyzLJnFCqehkZ.jpg");
    assertThat(tvResult.getVoteCount()).isEqualTo(14);
  }
}
