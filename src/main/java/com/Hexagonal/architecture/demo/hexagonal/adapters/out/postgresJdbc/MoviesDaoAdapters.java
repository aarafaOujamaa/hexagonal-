package com.Hexagonal.architecture.demo.hexagonal.adapters.out.postgresJdbc;

import com.Hexagonal.architecture.demo.hexagonal.adapters.out.postgresJdbc.entites.MovieEntity;
import com.Hexagonal.architecture.demo.hexagonal.adapters.out.postgresJdbc.repositories.MovieRepository;
import com.Hexagonal.architecture.demo.hexagonal.application.dao.MovieDao;
import com.Hexagonal.architecture.demo.hexagonal.application.dto.NewMovieDto;
import com.Hexagonal.architecture.demo.hexagonal.domain.Movie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class MoviesDaoAdapters implements MovieDao {
    private final MovieRepository  movieRepository;

    @Override
    public Optional<Movie> findMovieByTitle(String title) {

        return movieRepository.findByTitle(title);
    }

    @Override
    public List<Movie> findAllMovies() {
        List<MovieEntity> movies = (List<MovieEntity>) movieRepository.findAll();
        return movies.stream().map(movieEntity ->
                new Movie(movieEntity.id(), movieEntity.title(),
                        movieEntity.description(), movieEntity.releaseDate(),
                        movieEntity.directorName())).toList();
    }

    @Override
    public void saveMovie(NewMovieDto movie) {
        movieRepository.save(new MovieEntity(null,
                movie.title(),
                movie.description(),
                movie.releaseDate(),
                movie.directorName(),
                null));
    }

    @Override
    public void updateMovie(Movie movie) {
        movieRepository.save(new MovieEntity(movie.id(),
                movie.title(),  movie.description(),
                movie.releaseDate(), movie.directorName(), null));
    }

    @Override
    public void deleteMovie(Movie oldMovie) {

    }
}
