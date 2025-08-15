package com.rby.demo.sprinai_demo.dto.response;

import java.util.List;

/**
 * A highly structured response for representing a detailed actor profile. * This DTO is the target
 * for a BeanOutputParser to extract complex, nested information.
 *
 * @param actorName   The full name of the actor.
 * @param birthYear   The year the actor was born.
 * @param summary     A brief biography or summary of their career.
 * @param filmography A list of notable movies or shows, including their role.
 */
@SuppressWarnings("name must be changed")
public record StructuredResponse(
    String actorName,
    int birthYear,
    String summary,
    List<Movie> filmography
) {

  /**
   * Nested record to represent a single movie in the actor's filmography.
   *
   * @param title The title of the movie.
   * @param year  The release year of the movie.
   * @param role  The name of the character the actor played.
   */
  public record Movie(String title, int year, String role) {

  }
}