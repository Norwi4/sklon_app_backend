--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

-- Заказ
DROP TABLE IF EXISTS sklon_rent.order;
CREATE TABLE sklon_rent.order
(
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    client_id uuid NOT NULL,
    data_open DATE DEFAULT NOW(),
    deposit INT NOT NULL,

    FOREIGN KEY	(client_id) REFERENCES sklon_auth.client(id)
);


