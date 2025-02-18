package com.Hexagonal.architecture.demo.hexagonal.adapters.out.postgresJdbc.repositories;

import com.Hexagonal.architecture.demo.hexagonal.adapters.out.postgresJdbc.entites.MovieEntity;
import com.Hexagonal.architecture.demo.hexagonal.domain.Movie;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MovieRepository extends CrudRepository<MovieEntity, Long> {
    @Query("SELECT * FROM movies WHERE title = :title")
    Optional<Movie> findByTitle(@Param("title") String title);
}
