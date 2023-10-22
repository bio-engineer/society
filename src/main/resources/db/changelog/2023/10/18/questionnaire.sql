--liquibase formatted sql
--changeset aorlyanskiy:3 logicalFilePath:/usr/questionnaire

alter table usr.questionnaire
    add middle_name varchar(100);

comment on column usr.questionnaire.middle_name is 'Отчество';

