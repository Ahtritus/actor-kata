package org.incubyte.actors;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Singleton
public class PeopleService {
  private TmbdClient tmbdClient;

  @Value("${tmdb.api-key}")
  private String apiKey;

  public PeopleService(TmbdClient tmbdClient) {
    this.tmbdClient = tmbdClient;
  }

  public Optional<List<SearchResult>> searchByName(String name) {
    Optional<Page> mayBeResultWrapper = tmbdClient.searchByName(name, apiKey);
    Page page = mayBeResultWrapper.get();
    List<SearchResult> results = page.getResults();
    if (results.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(results);
  }

  public Optional<Person> getById(int id) {
    Optional<Person> byId = this.tmbdClient.getById(id, apiKey);
    Person person = byId.get();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
    try {
      Date birthDate = format.parse(person.getBirthday());
      int year = birthDate.getYear();
      long millis = Clock.systemDefaultZone().millis();
      int thisYear = new Date(millis).getYear();
      person.setAge(thisYear - year);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return byId;
  }

  public Optional<List<Movie>> getMovieDetails(int personId) {
    Optional<MovieWrapperDto> movies = tmbdClient.getMovies(personId, apiKey);
    return Optional.ofNullable(movies.get().getCast());
  }

  public Optional<List<TV>> getTVShowDetails(int personId) {
    Optional<TVWrapperDto> tvShows = tmbdClient.getTVShows(personId, apiKey);
    return Optional.ofNullable(tvShows.get().getCast());
  }


  public Optional<List<SearchResult>> getPopular() {
    Optional<Page> mayBeResultWrapper = tmbdClient.getPopular(apiKey);
    Page page = mayBeResultWrapper.get();
    List<SearchResult> results = page.getResults();
    if (results.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(results);
  }

  public Optional<List<Company>> searchByCompany(String company) {
    Optional<CompanyPage> mayBeResultWrapper = tmbdClient.searchByCompany(company, apiKey);
    CompanyPage companyPage = mayBeResultWrapper.get();
    List<Company> results = companyPage.getResults();
    if (results.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(results);

  }
}
