--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

-- Виды спорта
DROP TABLE IF EXISTS sklon_rent.categories_sport;
CREATE TABLE sklon_rent.categories_sport
(
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    sport_name VARCHAR(50)
);

INSERT INTO sklon_rent.categories_sport (id, sport_name)
VALUES (gen_random_uuid(), 'Snowboard'),
       (gen_random_uuid(), 'Ski');
