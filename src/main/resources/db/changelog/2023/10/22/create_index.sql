--liquibase formatted sql
--changeset aorlyanskiy:4 logicalFilePath:/usr/questionnaire

CREATE EXTENSION IF NOT EXISTS pg_trgm;
CREATE INDEX if not exists idx_usr_questionnaire_names ON usr.questionnaire USING gin (first_name gin_trgm_ops, second_name gin_trgm_ops);
