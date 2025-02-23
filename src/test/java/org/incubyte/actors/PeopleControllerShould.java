package org.incubyte.actors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PeopleControllerShould {
  @Mock PeopleService peopleService;

  @Test
  public void invoke_service_to_retrieve_search_results() {
    PeopleController peopleController = new PeopleController(peopleService);
    Optional<List<SearchResult>> results = peopleController.searchByName("tom cruise");
    verify(peopleService).searchByName("tom cruise");
  }

  @Test
  public void invoke_service_to_get_person_by_id() {
    PeopleController peopleController = new PeopleController(peopleService);
    Optional<Person> person = peopleController.getById(500);
    verify(peopleService).getById(500);
  }

  @Test
  public void invoke_service_to_get_movies_detail_by_person_id() {
    PeopleController peopleController = new PeopleController(peopleService);
    Optional<List<Movie>> movies = peopleController.getMovieDetails(500);
    verify(peopleService).getMovieDetails(500);
  }

  @Test
  public void invoke_service_to_get_tv_shows_detail_by_person_id() {
    PeopleController peopleController = new PeopleController(peopleService);
    Optional<List<TV>> tvShows = peopleController.getTVShowDetails(500);
    verify(peopleService).getTVShowDetails(500);
  }

  @Test
  public void invoke_service_to_retrieve_list_of_popular_persons() {
    PeopleController peopleController = new PeopleController(peopleService);
    Optional<List<SearchResult>> results = peopleController.getPopular();
    verify(peopleService).getPopular();
  }

  @Test
  public void invoke_service_to_retrieve_company_search_results() {
    PeopleController peopleController = new PeopleController(peopleService);
    Optional<List<Company>> results = peopleController.getCompanies("disney");
    verify(peopleService).searchByCompany("disney");
  }

}
