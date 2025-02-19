1️⃣ Adapters (Ports & Adapters) - Interface avec le monde extérieur
Cette couche contient tout ce qui concerne l'entrée et la sortie de l'application. Elle sert d'interface entre le système et les utilisateurs (API REST) ou les bases de données.

📂 adapters/in/web (Entrée - Web)
👉 Responsable de l'interaction avec les clients (API REST, UI, etc.)

Contient MovieController, qui gère les requêtes HTTP (REST API).
Utilise @RestController pour exposer des endpoints.
Fait appel aux cas d'utilisation (services métier) pour exécuter la logique.
📂 adapters/out (Sortie - Accès aux données)
👉 Responsable de l'accès aux bases de données (ou autres systèmes externes comme APIs tierces).

mongoJdbc et postgresJdbc indiquent que l’application supporte plusieurs bases de données.
entities contient MovieEntity, qui représente les objets stockés en base de données.
repositories contient MovieRepository, une interface pour interagir avec la base de données via Spring Data JPA.
MoviesDaoAdapters sert d’adaptateur entre la couche métier et la base de données. Il permet de découpler la logique métier de l’implémentation technique (JPA, JDBC, MongoDB, etc.).
2️⃣ Application (Logique Métier) - Cœur de l'application
👉 Cette couche contient la logique métier et les règles fonctionnelles.

📂 application/usecases
Contient MovieUseCase, qui définit les cas d'utilisation de l'application.
Il contient la logique métier principale, comme récupérer tous les films, chercher un film par titre, ajouter un nouveau film, etc.
C’est ici que l’on applique les règles métier et validations, indépendamment des détails techniques (pas de dépendance directe à la base de données).
📂 application/dao
MovieDao est une couche intermédiaire qui permet d’accéder aux données via un pattern DAO (Data Access Object).
Il fait le lien entre la logique métier (usecases) et les adaptateurs de persistance (repositories).
📂 application/dto (Data Transfer Objects)
Contient NewMovieDto, qui sert à transférer les données entre les différentes couches de l’application.
Utilité : éviter d’exposer directement les entités de la base de données et adapter les formats de données envoyés/recus.
3️⃣ Domain (Modèle Métier) - L'essence du métier
👉 Contient les objets et concepts métier principaux de l’application

📂 domain
Contient Movie, qui est probablement l’entité métier principale.
Contrairement à MovieEntity (qui dépend de la base de données), Movie est un objet métier pur et ne dépend pas de JPA ou Spring.
Il peut contenir des méthodes métier comme isAvailable() ou calculateRating().
4️⃣ Infrastructure (Gestion technique et exceptions)
👉 Gère les aspects techniques et les exceptions

📂 infrastructure/exceptions
Contient MovieAlreadyExistsException et MovieNotFoundException, qui permettent de gérer proprement les erreurs.
Ces exceptions sont spécifiques au métier et permettent de remonter des erreurs claires aux utilisateurs.
💡 Pourquoi cette architecture est bien pensée ?
✅ Séparation claire des responsabilités : chaque couche a un rôle précis.
✅ Modularité : possibilité de changer la base de données sans impacter la logique métier.
✅ Testabilité : la couche application peut être testée indépendamment des bases de données.
✅ Évolutivité : facile d'ajouter de nouveaux cas d'utilisation ou de nouvelles sources de données.

📌 Résumé rapide des responsabilités de chaque couche
Couche	Rôle
adapters/in/web	Expose les endpoints REST (MovieController)
adapters/out	Gère la persistance en base de données (MovieRepository, MoviesDaoAdapters)
application/usecases	Contient la logique métier principale (MovieUseCase)
application/dao	Intermédiaire entre la logique métier et la base de données
application/dto	Transfère les données entre les couches
domain	Définit les entités métier pures (Movie)
infrastructure/exceptions	Gère les erreurs et exceptions métier

-------------------------------------------*-------------------------------------------------------*-------------------------------
Flyway et JPA : Comment fonctionnent-ils ensemble ?
Flyway et JPA sont deux outils complémentaires qui permettent de gérer les bases de données dans une application Spring Boot. Voici comment ils interagissent :

📌 1. Flyway : Gestion des Migrations de Base de Données
Flyway est un outil de migration de base de données qui permet d’écrire et d’exécuter des scripts SQL versionnés pour créer, modifier ou supprimer des tables et données.

✅ Pourquoi utiliser Flyway ?

Versionne la base de données et garde un historique des modifications.
Assure la compatibilité entre les versions du code et la structure de la base de données.
Permet d’avoir une base de données cohérente pour tous les développeurs et les environnements (dev, test, prod).
✅ Exemple : Ajouter une table movies avec Flyway
Dans src/main/resources/db/migration/V1__create_movies_table.sql :

sql
Copier
Modifier
CREATE TABLE movies (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    director VARCHAR(255),
    release_year INT
);
🛠 Flyway applique ce script automatiquement au démarrage de l’application si flyway.enabled=true (config par défaut).

📌 2. JPA (Java Persistence API) : Gestion des Entités et Requêtes
JPA est une spécification Java pour la gestion des bases de données avec un mapping objet-relationnel (ORM). Spring Boot utilise Hibernate comme implémentation par défaut de JPA.

✅ Pourquoi utiliser JPA ?

Permet d’interagir avec la base de données sans écrire du SQL directement.
Gère la persistance avec des entités (@Entity) et des repositories (JpaRepository).
Automatiquement compatible avec différentes bases de données (PostgreSQL, MySQL, etc.).
✅ Exemple : Définir l’entité Movie avec JPA

java
Copier
Modifier
@Entity
@Table(name = "movies") // Correspond à la table Flyway
public class Movie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String director;
    private int releaseYear;

    // Getters, Setters et Constructeurs
}
✅ Exemple : Repository JPA pour accéder aux données

java
Copier
Modifier
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);
}
📌 3. Comment Flyway et JPA travaillent ensemble ?
📌 Étape 1 : Flyway crée les tables dans la base de données
Au démarrage, Spring Boot exécute les scripts SQL Flyway (V1__create_movies_table.sql).
Flyway s'assure que la structure de la base est correcte avant que JPA ne l’utilise.
📌 Étape 2 : JPA mappe les entités sur ces tables
JPA ne crée pas la table, il utilise celle créée par Flyway (movies).
Hibernate (JPA) mappe la classe Movie avec la table movies et permet d’y insérer/lire des données.
📌 Étape 3 : Les données sont accessibles via JPA
L’application peut maintenant insérer, modifier et lire les données sans se soucier du SQL.
Exemple d’utilisation avec MovieRepository :
java
Copier
Modifier
Movie movie = new Movie();
movie.setTitle("Inception");
movie.setDirector("Christopher Nolan");
movie.setReleaseYear(2010);
movieRepository.save(movie); // Hibernate gère l'INSERT automatiquement
📌 4. Configuration de Flyway et JPA dans application.properties
properties
Copier
Modifier
# Configuration de la base de données (PostgreSQL)
spring.datasource.url=jdbc:postgresql://localhost:5432/moviedb
spring.datasource.username=postgres
spring.datasource.password=secret

# Activer Flyway
spring.flyway.enabled=true

# Désactiver la création automatique des tables par JPA (car Flyway s'en occupe)
spring.jpa.hibernate.ddl-auto=validate
💡 Important :

spring.jpa.hibernate.ddl-auto=validate empêche JPA de créer/modifier les tables, car Flyway gère les migrations.
Si ddl-auto=update, Hibernate essaiera de modifier la base de données, ce qui peut entrer en conflit avec Flyway.
📌 5. Résumé : Qui fait quoi ?
Outil	Rôle
Flyway	Crée et met à jour la structure des tables avec des scripts SQL versionnés.
JPA (Hibernate)	Gère la persistance des objets Java (CRUD, requêtes).
Spring Data JPA	Fournit un accès simplifié aux bases de données avec JpaRepository.
📌 Conclusion
🚀 Flyway et JPA travaillent ensemble pour une gestion propre et robuste des bases de données :
✅ Flyway assure que la base de données a la bonne structure avant que l’application ne tourne.
✅ JPA (avec Hibernate) permet d’interagir avec la base sans écrire de SQL.

Cette approche évite les problèmes de synchronisation entre le code et la base de données, notamment en équipe et en production. 🔥
