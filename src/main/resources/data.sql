-- CREATE DATABASE price_engine_calculator;

CREATE TABLE parameter (
    parameter_id              bigint NOT NULL CONSTRAINT parameter_pkey PRIMARY KEY,
    carton_discount           DOUBLE PRECISION,
    discount_eligible_cartons INTEGER,
    labor_percentage          DOUBLE PRECISION
);


CREATE TABLE products (
    product_id       bigserial CONSTRAINT products_pkey PRIMARY KEY,
    price            DOUBLE PRECISION,
    product_label    VARCHAR(255),
    product_name     VARCHAR(255),
    units_per_carton INTEGER
);


INSERT INTO public.parameter (parameter_id, carton_discount, discount_eligible_cartons, labor_percentage) VALUES (1, 0.1, 3, 0.3);

INSERT INTO public.products (product_id, product_label, price, product_name, units_per_carton) VALUES (1, 'Rare', 175, 'Penguin-ears', 20);
INSERT INTO public.products (product_id, product_label, price, product_name, units_per_carton) VALUES (2, '', 825, 'Horseshoe', 5);