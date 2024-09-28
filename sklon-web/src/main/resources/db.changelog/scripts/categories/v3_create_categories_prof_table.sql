--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

-- Уровень профессионализма
DROP TABLE IF EXISTS sklon_rent.categories_prof;
CREATE TABLE sklon_rent.categories_prof
(
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    prof_level VARCHAR(50)
);

INSERT INTO sklon_rent.categories_prof (id, prof_level)
VALUES (gen_random_uuid(), 'Junior'),  -- детский
       (gen_random_uuid(), 'Comfort'), -- нач < 90кг
       (gen_random_uuid(), 'Premium'), --  нач > 90кг
       (gen_random_uuid(), 'Race'),    -- профи подготовленные трассы
       (gen_random_uuid(), 'Extreme'); -- фрирайд
