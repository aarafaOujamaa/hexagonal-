package com.Hexagonal.architecture.demo.hexagonal.application.dto;

import java.time.LocalDate;

public record NewMovieDto(String title,
                          String description,
                          LocalDate releaseDate,
                          String directorName) {
}
