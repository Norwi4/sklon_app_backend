--liquibase formatted sql
--changeSet your.name:1 runOnChange:true splitStatements:true

DROP TABLE IF EXISTS clients;

--changeSet your.name:2 runOnChange:true splitStatements:true
CREATE TABLE clients
(
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),  -- id клиента типа UUID, создается автоматически
    lastname VARCHAR(100),  -- Фамилия
    firstname VARCHAR(100), -- Имя
    patronymic VARCHAR(100) -- Отчество
) COMMENT 'Клиенты';


