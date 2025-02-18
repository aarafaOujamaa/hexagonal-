package com.Hexagonal.architecture.demo.hexagonal.application.usecases;


import com.Hexagonal.architecture.demo.hexagonal.application.dao.MovieDao;
import com.Hexagonal.architecture.demo.hexagonal.application.dto.NewMovieDto;
import com.Hexagonal.architecture.demo.hexagonal.domain.Movie;
import com.Hexagonal.architecture.demo.hexagonal.infrastructure.exceptions.MovieAlreadyExistsException;
import com.Hexagonal.architecture.demo.hexagonal.infrastructure.exceptions.MovieNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieUseCase {

    private final MovieDao movieDao;

    public String saveMovie(NewMovieDto newMovieDto) throws MovieAlreadyExistsException {
        // check if the movie already exists in db
        boolean isPresent = movieDao.findMovieByTitle(newMovieDto.title()).isPresent();
        if(!isPresent) {
            throw  new MovieAlreadyExistsException("Movie already exists");
        }
        // save the movie
        movieDao.saveMovie(newMovieDto);
        return "Movie saved successfully";
    }
    public List<Movie> getAllMovies() {
        return movieDao.findAllMovies();
    }

    public String updateMovie(Movie movie) throws MovieNotFoundException {
        boolean isPresent = movieDao.findMovieByTitle(movie.title()).isPresent();
        if(!isPresent) {
            throw  new MovieNotFoundException("this movie does not exist");
        }
        movieDao.updateMovie(movie);
        return "Movie saved successfully";
    }

    public Movie getMovieByTitle(String title) throws MovieNotFoundException {
        return movieDao.findMovieByTitle(title).orElseThrow(
                () -> new MovieNotFoundException("this movie does not exist"));
    }

}
