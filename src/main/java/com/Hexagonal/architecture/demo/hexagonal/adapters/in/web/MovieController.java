package com.Hexagonal.architecture.demo.hexagonal.adapters.in.web;

import com.Hexagonal.architecture.demo.hexagonal.application.dto.NewMovieDto;
import com.Hexagonal.architecture.demo.hexagonal.application.usecases.MovieUseCase;
import com.Hexagonal.architecture.demo.hexagonal.domain.Movie;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieUseCase movieUseCase;

    @GetMapping
    ResponseEntity<Object> getAllMovies() {
        return ResponseEntity.ok(movieUseCase.getAllMovies());
    }

    @GetMapping("/{title}")
    ResponseEntity<Object> getAllMovies(@PathVariable("title") String  title) {
        return ResponseEntity.ok(movieUseCase.getMovieByTitle(title));
    }

    @PostMapping
    ResponseEntity<Object> saveMovie(@RequestBody NewMovieDto newMovieDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(movieUseCase.saveMovie(newMovieDto));
    }

    @PutMapping
    ResponseEntity<Object> saveMovie(@RequestBody Movie movie) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(movieUseCase.updateMovie(movie));
    }
}
