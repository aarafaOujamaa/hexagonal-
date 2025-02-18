package com.Hexagonal.architecture.demo.hexagonal.domain;

import java.time.LocalDate;

public record Movie(
        Long id,
        String title,
        String description,
        LocalDate releaseDate,
        String directorName
) {
}
