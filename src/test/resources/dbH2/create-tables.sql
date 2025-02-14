-- DROP TABLE IF EXISTS film_actor;
-- DROP TABLE IF EXISTS film;
-- DROP TABLE IF EXISTS directors;
-- DROP TABLE IF EXISTS actors;

create table actors
(
    id      int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name    varchar(50) not null,
    surname varchar(50) not null,
    dob     date        not null,
    city    varchar(50) not null
);

create table directors
(
    id      int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name    varchar(50) not null,
    surname varchar(50) not null,
    city    varchar(50) not null
);

create table film
(
    id          int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title       varchar(100) not null,
    release_date        timestamp(6) not null,
    category    varchar(50)  not null,
    id_director int null,
    constraint film_ibfk_1
        foreign key (id_director) references directors (id)
);

create index id_director
    on film (id_director);

create table film_actor
(
    id_film  int not null,
    id_actor int not null,
    primary key (id_film, id_actor),
    constraint film_actor_ibfk_1
        foreign key (id_film) references film (id),
    constraint film_actor_ibfk_2
        foreign key (id_actor) references actors (id)
);

create index id_actor
    on film_actor (id_actor);

