create database if not exists netflux;
use netflux;
create table actors
(
    id      int auto_increment
        primary key,
    name    varchar(50) not null,
    surname varchar(50) not null,
    dob     date        null,
    city    varchar(50) not null
);

create table directors
(
    id      int auto_increment
        primary key,
    name    varchar(50) not null,
    surname varchar(50) not null,
    city    varchar(50) not null
);

create table film
(
    id           int auto_increment
        primary key,
    title        varchar(100) not null,
    release_date date         not null,
    category     varchar(50)  not null,
    id_director  int          null,
    poster       varchar(255) null,
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

create table users
(
    id    int auto_increment
        primary key,
    name  varchar(255) not null,
    email varchar(255) not null,
    constraint email_uk
        unique (email)
);

