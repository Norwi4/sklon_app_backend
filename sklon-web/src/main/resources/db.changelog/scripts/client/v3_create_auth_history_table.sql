--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false

-- Клиент
DROP TABLE IF EXISTS sklon_auth.auth_history;
CREATE TABLE sklon_auth.auth_history
(
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    client_id uuid NOT NULL,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES sklon_auth.clients(id) ON DELETE CASCADE
);
