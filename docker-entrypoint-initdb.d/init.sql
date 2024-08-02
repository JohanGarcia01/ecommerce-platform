CREATE TABLE IF NOT EXISTS products
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    description TEXT
);

CREATE TABLE IF NOT EXISTS orders
(
    id         SERIAL PRIMARY KEY,
    order_date TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total      DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS order_items
(
    id         SERIAL PRIMARY KEY,
    order_id   INTEGER REFERENCES orders (id),
    product_id INTEGER REFERENCES products (id),
    quantity   INTEGER NOT NULL
);
