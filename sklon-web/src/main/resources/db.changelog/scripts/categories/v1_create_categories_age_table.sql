--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

--Категории возраста
DROP TABLE IF EXISTS sklon_rent.categories_age;
CREATE TABLE sklon_rent.categories_age
(
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    age VARCHAR(3)
)
