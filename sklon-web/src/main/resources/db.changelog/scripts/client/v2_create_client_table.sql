--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

-- Клиент
DROP TABLE IF EXISTS sklon_auth.clients;
CREATE TABLE sklon_auth.clients
(
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    phone VARCHAR(50),
    code VARCHAR(50),
    token VARCHAR(50)
);
