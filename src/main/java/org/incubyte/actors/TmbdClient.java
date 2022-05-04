package org.incubyte.actors;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

import java.util.Optional;

@Client("https://api.themoviedb.org/3/")
public interface TmbdClient {
  @Get("search/person{?query}{&api_key}")
  Optional<Page> searchByName(@QueryValue String query, @QueryValue String api_key);

  @Get("person/{id}{?api_key}")
  Optional<Person> getById(int id, @QueryValue String api_key);

  @Get("person/{id}/movie_credits{?api_key}")
  Optional<MovieWrapperDto> getMovies(int id, @QueryValue String api_key);

  @Get("person/{id}/tv_credits{?api_key}")
  Optional<TVWrapperDto> getTVShows(int id, @QueryValue String api_key);

  @Get("person/popular{?api_key}")
  Optional<Page> getPopular(@QueryValue String api_key);

  @Get("search/company{?query}{&api_key}")
  Optional<CompanyPage> searchByCompany(String query, @QueryValue String api_key);
}
