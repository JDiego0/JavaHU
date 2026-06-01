-- V4: Ajustes de datos para criterios de busqueda parcial e insensible.
-- Mantiene 200 eventos y agrega una categoria especifica para probar "rock".

UPDATE venues
SET city = 'Bogota'
WHERE id = 1;

UPDATE events
SET name = 'Concierto de ROCK',
    description = 'Concierto de rock para validar busqueda por categoria y ciudad'
WHERE id = 1;

INSERT INTO categories (name, description)
SELECT 'Rock', 'Eventos musicales de rock'
WHERE NOT EXISTS (
    SELECT 1 FROM categories WHERE LOWER(name) = 'rock'
);

INSERT INTO events_categories (event_id, category_id)
SELECT 1, c.id
FROM categories c
WHERE LOWER(c.name) = 'rock'
  AND NOT EXISTS (
      SELECT 1
      FROM events_categories ec
      WHERE ec.event_id = 1
        AND ec.category_id = c.id
  );
