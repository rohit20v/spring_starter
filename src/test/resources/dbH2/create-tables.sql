create
or replace table actors
(
    id      int auto_increment
        primary key,
    name    varchar(50)  not null,
    surname varchar(50)  not null,
    dob     timestamp(6) not null,
    city    varchar(50)  not null
);

create
or replace table directors
(
    id      int auto_increment
        primary key,
    name    varchar(50) not null,
    surname varchar(50) not null,
    city    varchar(50) not null
);

create
or replace table film
(
    id          int auto_increment
        primary key,
    title       varchar(100) not null,
    date        timestamp(6) not null,
    category    varchar(50)  not null,
    id_director int          null,
    constraint film_ibfk_1
        foreign key (id_director) references directors (id)
);

create
or replace index id_director
    on film (id_director);

create
or replace table film_actor
(
    id_film  int not null,
    id_actor int not null,
    primary key (id_film, id_actor),
    constraint film_actor_ibfk_1
        foreign key (id_film) references film (id),
    constraint film_actor_ibfk_2
        foreign key (id_actor) references actors (id)
);

create
or replace index id_actor
    on film_actor (id_actor);

