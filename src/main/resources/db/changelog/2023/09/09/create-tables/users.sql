--liquibase formatted sql
--changeset aorlyanskiy:1 logicalFilePath:/usr/users

create sequence usr.users_s;

--changeset aorlyanskiy:2 logicalFilePath:/usr/users

create table usr.users
(
    id       bigint default nextval('usr.users_s') not null,
    password varchar                               not null,
    constraint users_pk primary key (id)
);

comment on table usr.users is 'Пользователи';
comment on column usr.users.id is 'Уникальный ИД пользователя';
comment on column usr.users.password is 'Пароль';
