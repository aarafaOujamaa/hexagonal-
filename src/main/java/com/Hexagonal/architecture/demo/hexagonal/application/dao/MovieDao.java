package com.Hexagonal.architecture.demo.hexagonal.application.dao;

import com.Hexagonal.architecture.demo.hexagonal.application.dto.NewMovieDto;
import com.Hexagonal.architecture.demo.hexagonal.domain.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieDao {
    Optional<Movie> findMovieByTitle(String title);

    List<Movie> findAllMovies();

    void saveMovie(NewMovieDto movie);

    void updateMovie(Movie movie);

    void deleteMovie(Movie oldMovie);


}
