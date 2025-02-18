CREATE SEQUENCE movies_table_id_seq;

CREATE TABLE movies_table (
    id BIGINT primary key  DEFAULT nextval('movies_table_id_seq'),
    name varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    releaseDate date NOT NULL,
    director varchar(255) NOT NULL,
    version integer
);