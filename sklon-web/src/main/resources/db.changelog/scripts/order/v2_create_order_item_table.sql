--liquibase formatted sql
--changeSet runOnChange:true splitStatements:false


-- Заказ
DROP TABLE IF EXISTS sklon_rent.order_item;
CREATE TABLE sklon_rent.order_item
(
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    product_id uuid NOT NULL,
    order_id uuid NOT NULL,

    FOREIGN KEY	(product_id) REFERENCES sklon_rent.product(id),
    FOREIGN KEY	(order_id) REFERENCES sklon_rent.order(id)
);
