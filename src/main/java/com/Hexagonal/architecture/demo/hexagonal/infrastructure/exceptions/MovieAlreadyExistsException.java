package com.Hexagonal.architecture.demo.hexagonal.infrastructure.exceptions;

public class MovieAlreadyExistsException extends RuntimeException {


  public MovieAlreadyExistsException(String message) {
        super(message);
    }
}
