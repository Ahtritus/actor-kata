package org.incubyte.actors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeopleServiceShould {
  private final Clock clock =
      Clock.fixed(
          ZonedDateTime.parse("2021-10-25T00:00:00.000+09:00[Asia/Seoul]").toInstant(),
          ZoneId.of("Asia/Seoul"));
  @Mock TmbdClient tmbdClient;
  Page page;
  Page page2;
  Person person;
  MovieWrapperDto movieWrapperDto;
  List<Movie> movies;
  TVWrapperDto tvWrapperDto;
  List<TV> tvShows;

  CompanyPage companyPage;
  Company company;

  @BeforeEach
  public void init() {
    page = new Page();
    // create result object
    SearchResult searchResult1 = new SearchResult();
    searchResult1.setName("Tom Cruise");
    searchResult1.setProfilePath("/Abc");
    SearchResult searchResult2 = new SearchResult();
    searchResult2.setName("Tom Cruise123");
    searchResult2.setProfilePath("/xyz");
    // add result object to list
    List<SearchResult> allResults = new ArrayList<SearchResult>();
    allResults.add(searchResult1);
    allResults.add(searchResult2);
    page.setResults(allResults);

    page2 = new Page();
    page2.setResults(new ArrayList<>());

    person = new Person();
    person.setBirthday("1962-07-03");

    movieWrapperDto = new MovieWrapperDto();
    movies = new ArrayList<>();
    movies.add(new Movie());
    movies.add(new Movie());
    movieWrapperDto.setCast(movies);

    tvWrapperDto = new TVWrapperDto();
    tvShows = new ArrayList<>();
    tvShows.add(new TV());
    tvShows.add(new TV());
    tvWrapperDto.setCast(tvShows);

    companyPage = new CompanyPage();
    company = new Company();
    company.setId(1);
    company.setLogoPath("asd");
    company.setName("test name");

    List<Company> companyResults = new ArrayList<>();
    companyResults.add(company);
    companyPage.setResults(companyResults);
  }

  @Test
  void invoke_http_client() {
    when(tmbdClient.searchByName("tom cruise", null)).thenReturn(Optional.of(page));
    PeopleService peopleService = new PeopleService(tmbdClient);

    Optional<List<SearchResult>> result = peopleService.searchByName("tom cruise");
    verify(tmbdClient).searchByName("tom cruise", null);
    assertThat(result).isPresent();
  }

  @Test
  public void return_empty_optional_when_no_results_found() {
    when(tmbdClient.searchByName("abc xyz", null)).thenReturn(Optional.of(page2));
    PeopleService peopleService = new PeopleService(tmbdClient);
    Optional<List<SearchResult>> results = peopleService.searchByName("abc xyz");
    assertThat(results).isEmpty();
  }

  @Test
  public void invoke_http_client_to_retrieve_person() {
    when(tmbdClient.getById(500, null)).thenReturn(Optional.of(person));
    var mockClock = mockStatic(Clock.class);
    when(clock.systemDefaultZone()).thenReturn(clock);
    PeopleService peopleService = new PeopleService(tmbdClient);
    Optional<Person> person = peopleService.getById(500);
    assertThat(person.get().getAge()).isEqualTo(59);
  }

  @Test
  void invoke_http_client_to_retrieve_list_of_movie_details() {
    when(tmbdClient.getMovies(500, null)).thenReturn(Optional.ofNullable(movieWrapperDto));
    PeopleService peopleService = new PeopleService(tmbdClient);
    Optional<List<Movie>> movieDetails = peopleService.getMovieDetails(500);
    verify(tmbdClient).getMovies(500, null);
  }

  @Test
  void invoke_http_client_to_retrieve_list_of_tv_show_details() {
    when(tmbdClient.getTVShows(500, null)).thenReturn(Optional.ofNullable(tvWrapperDto));
    PeopleService peopleService = new PeopleService(tmbdClient);
    Optional<List<TV>> tvDetails = peopleService.getTVShowDetails(500);
    verify(tmbdClient).getTVShows(500, null);
  }

  @Test
  void invoke_http_client_to_retrieve_list_of_popular_persons() {
    when(tmbdClient.getPopular(null)).thenReturn(Optional.of(page));
    PeopleService peopleService = new PeopleService(tmbdClient);

    Optional<List<SearchResult>> result = peopleService.getPopular();
    verify(tmbdClient).getPopular(null);
    assertThat(result).isPresent();
  }


  @Test
  public void invoke_http_client_to_retrieve_list_of_companies() {
    when(tmbdClient.searchByCompany("disney", null)).thenReturn(Optional.of(companyPage));
    PeopleService peopleService = new PeopleService(tmbdClient);

    Optional<List<Company>> result = peopleService.searchByCompany("disney");
    verify(tmbdClient).searchByCompany("disney", null);
    assertThat(result).isPresent();
  }
}
