package com.Hexagonal.architecture.demo.hexagonal.adapters.out.postgresJdbc.entites;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

//⚠️ Différence entre JPA et Spring Data JDBC :
//JPA (@jakarta.persistence.Table) → Utilisée avec Hibernate (ou tout autre ORM basé sur JPA).
// Spring Data JDBC (@org.springframework.data.relational.core.mapping.Table) → Utilisée avec Spring Data JDBC, qui est plus léger et ne supporte pas les relations complexes comme JPA.

@Table(name="movies") // Pas d'@Entity ici, car Spring Data JDBC ne gère pas les entités comme JPA
public record MovieEntity(
        @Id
        Long id,
        String title,
        String description,
        LocalDate releaseDate,
        String directorName,

        @Version
        Integer version
) {
}
