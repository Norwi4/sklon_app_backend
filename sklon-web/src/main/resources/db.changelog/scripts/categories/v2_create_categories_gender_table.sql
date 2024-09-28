--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

-- Категории пола
DROP TABLE IF EXISTS sklon_rent.categories_gender;
CREATE TABLE sklon_rent.categories_gender
(
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    gender VARCHAR(15)
);

INSERT INTO sklon_rent.categories_gender (id, gender)
VALUES (gen_random_uuid(), 'Man'), -- 'мж' для мужского
       (gen_random_uuid(), 'Woman'); -- 'жн' для женского

