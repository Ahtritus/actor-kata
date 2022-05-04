package org.incubyte.actors;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;

import java.util.List;
import java.util.Optional;

@Controller("/")
public class PeopleController {
  private final PeopleService peopleService;

  public PeopleController(PeopleService peopleService) {
    this.peopleService = peopleService;
  }

  @Get("people")
  public Optional<List<SearchResult>> searchByName(@QueryValue String query) {
    return peopleService.searchByName(query);
  }

  @Get("people/{id}")
  public Optional<Person> getById(int id) {
    return peopleService.getById(id);
  }

  @Get("people/{id}/movies")
  public Optional<List<Movie>> getMovieDetails(@PathVariable int id) {
    return peopleService.getMovieDetails(id);
  }

  @Get("people/{id}/tv")
  public Optional<List<TV>> getTVShowDetails(int id) {
    return peopleService.getTVShowDetails(id);
  }

  @Get("people/popular")
  public Optional<List<SearchResult>> getPopular() {
    return peopleService.getPopular();
  }

  @Get("/company")
  public Optional<List<Company>> getCompanies(@QueryValue String query) {
    return peopleService.searchByCompany(query);
  }
}
