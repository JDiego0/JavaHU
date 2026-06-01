-- =====================================================================
-- V1: Estructura inicial de tablas
-- Crea las tablas base: venues y events con relacion ManyToOne
-- =====================================================================

CREATE TABLE venues (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(200) NOT NULL,
    capacity INT NOT NULL,
    city VARCHAR(100) NOT NULL
);

CREATE TABLE events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    date TIMESTAMP NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    venue_id BIGINT NOT NULL,
    CONSTRAINT fk_event_venue FOREIGN KEY (venue_id) REFERENCES venues(id)
);

-- Indice para optimizar busquedas por ciudad
CREATE INDEX idx_venue_city ON venues(city);

-- Indice para optimizar busquedas por fecha
CREATE INDEX idx_event_date ON events(date);

-- Indice para filtro de soft delete
CREATE INDEX idx_event_active ON events(active);
