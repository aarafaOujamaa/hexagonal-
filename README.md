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
