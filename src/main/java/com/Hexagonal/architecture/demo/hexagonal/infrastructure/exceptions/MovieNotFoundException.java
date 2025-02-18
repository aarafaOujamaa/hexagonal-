package com.Hexagonal.architecture.demo.hexagonal.infrastructure.exceptions;

public class MovieNotFoundException extends RuntimeException {

    public MovieNotFoundException(String thisMovieDoesNotExist) {
        super(thisMovieDoesNotExist);
    }
}
