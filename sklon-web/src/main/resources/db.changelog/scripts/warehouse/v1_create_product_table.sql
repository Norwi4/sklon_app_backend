--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

DROP TABLE IF EXISTS sklon_rent.product_status;
CREATE TABLE sklon_rent.product_status
(
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255)      -- статус СВОБОДНО, ЗАБРОНИРОВАНО, НА РУКАХ
);

INSERT INTO sklon_rent.product_status (id, name)
VALUES (gen_random_uuid(), 'Free'),
       (gen_random_uuid(), 'Booked'),
       (gen_random_uuid(), 'Employed');


-- Товар
DROP TABLE IF EXISTS sklon_rent.product;
CREATE TABLE sklon_rent.product
(
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(255),       -- наименование оборудования
    sport_id uuid NOT NULL,  -- категории оборудования вид спорта  ссылка
    prof_id BIGINT NOT NULL, -- категория оборудования профи   ссылка
    gender_id uuid NOT NULL, -- пол   ссылка
    age_id uuid NOT NULL,    -- возраст  ссылка
    size VARCHAR(10),        -- размер (42, XXL, рост 185 и т.д.)
    price NUMERIC NOT NULL,  -- прайс (цена проката за один день)
    photo VARCHAR(255),      -- относительный путь к файлу в файлохранилище
    status_id uuid,       -- статус СВОБОДНО, ЗАБРОНИРОВАНО, НА РУКАХ

    FOREIGN KEY (sport_id)
        REFERENCES sklon_rent.categories_sport (id),
    FOREIGN KEY (gender_id)
        REFERENCES sklon_rent.categories_gender (id),
    FOREIGN KEY (age_id)
        REFERENCES sklon_rent.categories_age (id),
    FOREIGN KEY (status_id)
        REFERENCES sklon_rent.product_status (id)
);
