create table users (
    id serial primary key,
    id_user varchar,
    name varchar,
    given_name varchar,
    family_name varchar,
    email varchar unique
)