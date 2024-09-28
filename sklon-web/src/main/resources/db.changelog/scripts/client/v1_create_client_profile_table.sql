--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

-- Профиль клиента
DROP TABLE IF EXISTS sklon_rent.clients_profiles;
CREATE TABLE sklon_rent.clients_profiles
(
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    firstname VARCHAR(50),          -- имя
    lastname VARCHAR(100),          -- фамилия
    patronymic VARCHAR(100),        -- отчество
    home_street VARCHAR(100),       -- улица
    home_house VARCHAR(100),        -- дом
    home_apartment VARCHAR(100),    -- квартира
    client_id uuid UNIQUE NOT NULL, -- ссылка на мета информацию
    FOREIGN KEY (client_id) REFERENCES sklon_auth.clients (id) ON DELETE CASCADE
);
