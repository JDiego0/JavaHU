-- =====================================================================
-- V2: Evolucion relacional - Categorias y tabla intermedia
-- Crea la entidad Category y la relacion ManyToMany con Event
-- =====================================================================

CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500)
);

-- Tabla intermedia para la relacion ManyToMany entre Event y Category
-- Nombres de tabla y columnas definidos explicitamente
CREATE TABLE events_categories (
    event_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (event_id, category_id),
    CONSTRAINT fk_ec_event FOREIGN KEY (event_id) REFERENCES events(id),
    CONSTRAINT fk_ec_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Indices para optimizar JOINs en la tabla intermedia
CREATE INDEX idx_ec_event ON events_categories(event_id);
CREATE INDEX idx_ec_category ON events_categories(category_id);

-- Indice para busqueda por nombre de categoria
CREATE INDEX idx_category_name ON categories(name);
