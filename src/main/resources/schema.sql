-- =============================================
-- Crown Premium Chocolates - Schema SQL
-- Designed for PostgreSQL (Supabase)
-- =============================================

-- 1. Users table
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. Box Sizes (extensible catalog)
CREATE TABLE IF NOT EXISTS box_sizes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    capacity INT NOT NULL,
    base_price DECIMAL(10, 2) NOT NULL
);

-- 3. Chocolate Flavors (extensible catalog)
CREATE TABLE IF NOT EXISTS chocolate_flavors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- 4. Fillings (extensible catalog)
CREATE TABLE IF NOT EXISTS fillings (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- 5. Orders (with address snapshot)
CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE SET NULL,
    status VARCHAR(50) DEFAULT 'PENDIENTE_DEMO',
    total_price DECIMAL(10, 2) NOT NULL,
    calle_numero VARCHAR(255) NOT NULL,
    colonia VARCHAR(150) NOT NULL,
    codigo_postal VARCHAR(20) NOT NULL,
    ciudad VARCHAR(150) NOT NULL,
    estado VARCHAR(150) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 6. Order Items (each box in an order)
CREATE TABLE IF NOT EXISTS order_items (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    box_size_id INT NOT NULL REFERENCES box_sizes(id),
    quantity INT NOT NULL DEFAULT 1,
    unit_price DECIMAL(10, 2) NOT NULL
);

-- 7. Box Chocolates (each chocolate inside a box/order_item)
CREATE TABLE IF NOT EXISTS box_chocolates (
    id SERIAL PRIMARY KEY,
    order_item_id INT NOT NULL REFERENCES order_items(id) ON DELETE CASCADE,
    chocolate_flavor_id INT NOT NULL REFERENCES chocolate_flavors(id),
    filling_id INT NOT NULL REFERENCES fillings(id)
);

-- =============================================
-- Seed Data (Initial Catalog)
-- =============================================

INSERT INTO box_sizes (name, capacity, base_price) VALUES
    ('Chica', 6, 15.00),
    ('Grande', 12, 25.00)
ON CONFLICT (name) DO UPDATE SET capacity = EXCLUDED.capacity;

INSERT INTO chocolate_flavors (name) VALUES
    ('Dulce'),
    ('Amargo'),
    ('Blanco')
ON CONFLICT (name) DO NOTHING;

INSERT INTO fillings (name) VALUES
    ('Choco avellana'),
    ('Fresa'),
    ('Queso con zarzamora'),
    ('Almendra'),
    ('Mango')
ON CONFLICT (name) DO NOTHING;
