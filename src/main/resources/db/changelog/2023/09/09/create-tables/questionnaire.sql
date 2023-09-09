--liquibase formatted sql
--changeset aorlyanskiy:1 logicalFilePath:/usr/questionnaire

create table usr.questionnaire
(
    user_id     integer      not null,
    first_name  varchar(100) not null,
    second_name varchar(100),
    birthdate   date,
    biography   varchar,
    city        varchar
);

comment on table usr.questionnaire is 'Анкеты пользователей';

comment on column usr.questionnaire.user_id is 'ИД пользователя';
comment on column usr.questionnaire.first_name is 'Имя';
comment on column usr.questionnaire.second_name is 'Фамилия';
comment on column usr.questionnaire.birthdate is 'Дата рождения';
comment on column usr.questionnaire.biography is 'Биография';
comment on column usr.questionnaire.city is 'Город';

--changeset aorlyanskiy:2 logicalFilePath:/usr/questionnaire

alter table usr.questionnaire
    add constraint questionnaire_users_id_fk
        foreign key (user_id) references usr.users (id);