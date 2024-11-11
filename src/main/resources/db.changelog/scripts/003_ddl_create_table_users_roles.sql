create table users_roles(
    role_id int references roles(id),
    user_id int references user(id)
)