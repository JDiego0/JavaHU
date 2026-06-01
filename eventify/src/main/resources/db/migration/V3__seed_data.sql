-- =====================================================================
-- V3: Seed de datos masivo (200+ registros variados)
-- Venues (15), Categories (7), Events (200), Events_Categories (~350)
-- =====================================================================

-- ==================== VENUES (15 registros) ====================
INSERT INTO venues (name, address, capacity, city) VALUES
('Estadio Nacional', 'Av. Jose Diaz s/n', 50000, 'Lima'),
('Teatro Municipal', 'Jiron Ica 377', 800, 'Lima'),
('Centro de Convenciones LCC', 'Av. Javier Prado Este 2465', 3500, 'Lima'),
('Arena del Pacifico', 'Blvd. Costero 321', 15000, 'Callao'),
('Jardin Botanico', 'Av. Arequipa 4500', 500, 'Miraflores'),
('Coliseo Eduardo Dibos', 'Av. Aviacion 2800', 5000, 'San Borja'),
('Parque de la Exposicion', 'Av. 28 de Julio', 8000, 'Lima'),
('Gran Teatro Nacional', 'Av. Javier Prado Este 2225', 1500, 'San Borja'),
('Club Regatas', 'Malecon Grau 598', 2000, 'Chorrillos'),
('Centro Cultural PUCP', 'Av. Camino Real 1075', 400, 'San Isidro'),
('Estadio Monumental', 'Av. Javier Prado Este 7050', 80000, 'Ate'),
('Feria del Hogar', 'Av. Defensores del Morro', 10000, 'Chorrillos'),
('Auditorio AFP Integra', 'Calle Las Begonias 475', 300, 'San Isidro'),
('Plaza Arena', 'Av. Oscar R. Benavides 3866', 6000, 'Callao'),
('Huaca Pucllana', 'Calle General Borgoño 8va cuadra', 250, 'Miraflores');

-- ==================== CATEGORIES (7 registros) ====================
INSERT INTO categories (name, description) VALUES
('Conciertos', 'Eventos musicales en vivo con artistas nacionales e internacionales'),
('Talleres', 'Sesiones practicas de aprendizaje y desarrollo de habilidades'),
('Conferencias', 'Charlas y presentaciones sobre temas especializados'),
('Deportes', 'Competencias y eventos deportivos de alto rendimiento'),
('Gastronomia', 'Ferias, degustaciones y experiencias culinarias'),
('Festivales', 'Celebraciones culturales con multiples actividades'),
('Teatro', 'Obras teatrales, musicales y artes escenicas');

-- ==================== EVENTS (200 registros) ====================
-- Bloque 1: Conciertos y Festivales (eventos 1-40)
INSERT INTO events (name, description, date, active, venue_id) VALUES
('Rock en el Estadio', 'Gran concierto de rock con bandas nacionales', DATEADD('DAY', 10, CURRENT_TIMESTAMP), TRUE, 1),
('Jazz Night Lima', 'Noche de jazz con artistas internacionales', DATEADD('DAY', 12, CURRENT_TIMESTAMP), TRUE, 2),
('Cumbia Fest 2026', 'Festival de cumbia peruana', DATEADD('DAY', 15, CURRENT_TIMESTAMP), TRUE, 7),
('Electro Beach Party', 'Fiesta electronica en la playa', DATEADD('DAY', 18, CURRENT_TIMESTAMP), TRUE, 4),
('Reggaeton Live', 'Los mejores artistas de reggaeton', DATEADD('DAY', 20, CURRENT_TIMESTAMP), TRUE, 1),
('Sinfonico en el Gran Teatro', 'Orquesta Sinfonica Nacional', DATEADD('DAY', 22, CURRENT_TIMESTAMP), TRUE, 8),
('Indie Music Night', 'Bandas indie emergentes', DATEADD('DAY', 25, CURRENT_TIMESTAMP), TRUE, 10),
('Salsa Brava', 'Noche de salsa con orquestas en vivo', DATEADD('DAY', 27, CURRENT_TIMESTAMP), TRUE, 6),
('Pop Stars en Lima', 'Concierto pop internacional', DATEADD('DAY', 30, CURRENT_TIMESTAMP), TRUE, 11),
('Festival de Blues', 'Blues autentico en vivo', DATEADD('DAY', 32, CURRENT_TIMESTAMP), TRUE, 2),
('Metal Fest Lima', 'Festival de metal y rock pesado', DATEADD('DAY', 35, CURRENT_TIMESTAMP), TRUE, 4),
('Trova y Poesia Musical', 'Noche de trova latinoamericana', DATEADD('DAY', 37, CURRENT_TIMESTAMP), TRUE, 10),
('Hip Hop Summit', 'Lo mejor del hip hop nacional', DATEADD('DAY', 40, CURRENT_TIMESTAMP), TRUE, 6),
('Folklore Andino', 'Musica y danzas tradicionales', DATEADD('DAY', 42, CURRENT_TIMESTAMP), TRUE, 7),
('Festival Afroperuano', 'Celebracion de la musica afroperuana', DATEADD('DAY', 45, CURRENT_TIMESTAMP), TRUE, 9),
('Concierto Benefico', 'Musica por una buena causa', DATEADD('DAY', 47, CURRENT_TIMESTAMP), TRUE, 8),
('DJ Night Miraflores', 'Noche de DJs en Miraflores', DATEADD('DAY', 50, CURRENT_TIMESTAMP), TRUE, 15),
('Acoustic Sessions', 'Sesiones acusticas intimas', DATEADD('DAY', 52, CURRENT_TIMESTAMP), TRUE, 13),
('Latin Grammy Showcase', 'Artistas nominados al Grammy Latino', DATEADD('DAY', 55, CURRENT_TIMESTAMP), TRUE, 8),
('Fiesta Criolla', 'Musica criolla en vivo', DATEADD('DAY', 57, CURRENT_TIMESTAMP), TRUE, 9),
('Techno Underground', 'Evento techno en espacio alternativo', DATEADD('DAY', 60, CURRENT_TIMESTAMP), TRUE, 14),
('World Music Festival', 'Musica del mundo en Lima', DATEADD('DAY', 62, CURRENT_TIMESTAMP), TRUE, 7),
('Piano Recital', 'Recital de piano clasico', DATEADD('DAY', 65, CURRENT_TIMESTAMP), TRUE, 8),
('Festival de Verano', 'Gran festival de musica de verano', DATEADD('DAY', 67, CURRENT_TIMESTAMP), TRUE, 1),
('Noche de Boleros', 'Boleros romanticos en vivo', DATEADD('DAY', 70, CURRENT_TIMESTAMP), TRUE, 2),
('K-Pop Fest Lima', 'Festival de K-Pop y cultura coreana', DATEADD('DAY', 72, CURRENT_TIMESTAMP), TRUE, 6),
('Reggae Vibrations', 'Festival de reggae y ska', DATEADD('DAY', 75, CURRENT_TIMESTAMP), TRUE, 4),
('Musica Electroacustica', 'Fusion de electronica y acustico', DATEADD('DAY', 77, CURRENT_TIMESTAMP), TRUE, 10),
('Rock Clasico Tributo', 'Tributo a las grandes bandas de rock', DATEADD('DAY', 80, CURRENT_TIMESTAMP), TRUE, 11),
('Festival del Sol', 'Celebracion musical al aire libre', DATEADD('DAY', 82, CURRENT_TIMESTAMP), TRUE, 7),
('Opera en el Teatro', 'Temporada de opera internacional', DATEADD('DAY', 85, CURRENT_TIMESTAMP), TRUE, 8),
('Concierto de Navidad', 'Musica navidena con orquesta', DATEADD('DAY', 87, CURRENT_TIMESTAMP), TRUE, 8),
('Festival Urbano', 'Hip hop, trap y reggaeton', DATEADD('DAY', 90, CURRENT_TIMESTAMP), TRUE, 14),
('Noche Latina', 'Lo mejor de la musica latina', DATEADD('DAY', 92, CURRENT_TIMESTAMP), TRUE, 6),
('Bossa Nova Night', 'Noche de bossa nova brasilera', DATEADD('DAY', 95, CURRENT_TIMESTAMP), TRUE, 15),
('Festival de Guitarras', 'Guitarristas de talla mundial', DATEADD('DAY', 97, CURRENT_TIMESTAMP), TRUE, 2),
('Concierto de Ano Nuevo', 'Celebracion musical de ano nuevo', DATEADD('DAY', 100, CURRENT_TIMESTAMP), TRUE, 1),
('Flamenco en Lima', 'Espectaculo de flamenco espanol', DATEADD('DAY', 102, CURRENT_TIMESTAMP), TRUE, 8),
('Musica Amazonica', 'Sonidos de la selva peruana', DATEADD('DAY', 105, CURRENT_TIMESTAMP), TRUE, 10),
('Electro Carnival', 'Carnaval electronico de dos dias', DATEADD('DAY', 107, CURRENT_TIMESTAMP), TRUE, 4);

-- Bloque 2: Conferencias y Talleres (eventos 41-80)
INSERT INTO events (name, description, date, active, venue_id) VALUES
('Tech Summit 2026', 'Conferencia de tecnologia e innovacion', DATEADD('DAY', 11, CURRENT_TIMESTAMP), TRUE, 3),
('AI Workshop', 'Taller practico de inteligencia artificial', DATEADD('DAY', 13, CURRENT_TIMESTAMP), TRUE, 13),
('DevOps Conference', 'Mejores practicas en DevOps y CI/CD', DATEADD('DAY', 16, CURRENT_TIMESTAMP), TRUE, 3),
('Data Science Bootcamp', 'Bootcamp intensivo de ciencia de datos', DATEADD('DAY', 19, CURRENT_TIMESTAMP), TRUE, 10),
('Cloud Computing Summit', 'AWS, Azure y Google Cloud', DATEADD('DAY', 21, CURRENT_TIMESTAMP), TRUE, 3),
('UX Design Workshop', 'Taller de diseno de experiencia de usuario', DATEADD('DAY', 24, CURRENT_TIMESTAMP), TRUE, 13),
('Cybersecurity Forum', 'Seguridad informatica y proteccion de datos', DATEADD('DAY', 26, CURRENT_TIMESTAMP), TRUE, 3),
('Blockchain Conference', 'El futuro de blockchain y Web3', DATEADD('DAY', 29, CURRENT_TIMESTAMP), TRUE, 13),
('Mobile Dev Workshop', 'Desarrollo de apps moviles con Flutter', DATEADD('DAY', 31, CURRENT_TIMESTAMP), TRUE, 10),
('Startup Weekend', 'Emprendimiento e innovacion', DATEADD('DAY', 34, CURRENT_TIMESTAMP), TRUE, 3),
('Machine Learning Lab', 'Laboratorio practico de ML', DATEADD('DAY', 36, CURRENT_TIMESTAMP), TRUE, 13),
('Frontend Masters', 'Taller avanzado de React y Vue', DATEADD('DAY', 39, CURRENT_TIMESTAMP), TRUE, 10),
('Backend Architecture', 'Patrones de arquitectura backend', DATEADD('DAY', 41, CURRENT_TIMESTAMP), TRUE, 3),
('Python Workshop', 'Taller de Python para todos los niveles', DATEADD('DAY', 44, CURRENT_TIMESTAMP), TRUE, 13),
('Java Enterprise Summit', 'Java y Spring Boot en produccion', DATEADD('DAY', 46, CURRENT_TIMESTAMP), TRUE, 3),
('Agile Leadership', 'Liderazgo agil para equipos de desarrollo', DATEADD('DAY', 49, CURRENT_TIMESTAMP), TRUE, 10),
('IoT Conference', 'Internet de las cosas y automatizacion', DATEADD('DAY', 51, CURRENT_TIMESTAMP), TRUE, 3),
('Game Dev Workshop', 'Desarrollo de videojuegos con Unity', DATEADD('DAY', 54, CURRENT_TIMESTAMP), TRUE, 13),
('Digital Marketing Summit', 'Marketing digital y redes sociales', DATEADD('DAY', 56, CURRENT_TIMESTAMP), TRUE, 3),
('Photography Masterclass', 'Fotografia profesional avanzada', DATEADD('DAY', 59, CURRENT_TIMESTAMP), TRUE, 10),
('Fintech Forum', 'Tecnologia financiera y banca digital', DATEADD('DAY', 61, CURRENT_TIMESTAMP), TRUE, 3),
('Design Thinking Workshop', 'Innovacion con Design Thinking', DATEADD('DAY', 64, CURRENT_TIMESTAMP), TRUE, 13),
('Green Tech Conference', 'Tecnologia y sostenibilidad', DATEADD('DAY', 66, CURRENT_TIMESTAMP), TRUE, 3),
('Robotics Lab', 'Taller de robotica y automatizacion', DATEADD('DAY', 69, CURRENT_TIMESTAMP), TRUE, 10),
('E-commerce Summit', 'Comercio electronico y marketplace', DATEADD('DAY', 71, CURRENT_TIMESTAMP), TRUE, 3),
('Video Production Workshop', 'Produccion audiovisual profesional', DATEADD('DAY', 74, CURRENT_TIMESTAMP), TRUE, 13),
('EdTech Conference', 'Tecnologia educativa e innovacion', DATEADD('DAY', 76, CURRENT_TIMESTAMP), TRUE, 3),
('3D Printing Workshop', 'Impresion 3D y fabricacion digital', DATEADD('DAY', 79, CURRENT_TIMESTAMP), TRUE, 10),
('HealthTech Summit', 'Tecnologia en salud y bienestar', DATEADD('DAY', 81, CURRENT_TIMESTAMP), TRUE, 3),
('Creative Coding Lab', 'Arte generativo y programacion creativa', DATEADD('DAY', 84, CURRENT_TIMESTAMP), TRUE, 13),
('Big Data Conference', 'Procesamiento masivo de datos', DATEADD('DAY', 86, CURRENT_TIMESTAMP), TRUE, 3),
('Public Speaking Workshop', 'Oratoria y presentaciones efectivas', DATEADD('DAY', 89, CURRENT_TIMESTAMP), TRUE, 10),
('AR/VR Experience', 'Realidad aumentada y virtual', DATEADD('DAY', 91, CURRENT_TIMESTAMP), TRUE, 3),
('Content Creation Bootcamp', 'Creacion de contenido digital', DATEADD('DAY', 94, CURRENT_TIMESTAMP), TRUE, 13),
('Microservices Workshop', 'Arquitectura de microservicios', DATEADD('DAY', 96, CURRENT_TIMESTAMP), TRUE, 10),
('SaaS Business Summit', 'Negocios SaaS y escalabilidad', DATEADD('DAY', 99, CURRENT_TIMESTAMP), TRUE, 3),
('Ethical Hacking Lab', 'Hacking etico y pentesting', DATEADD('DAY', 101, CURRENT_TIMESTAMP), TRUE, 13),
('Product Management Forum', 'Gestion de producto digital', DATEADD('DAY', 104, CURRENT_TIMESTAMP), TRUE, 3),
('Quantum Computing Intro', 'Introduccion a computacion cuantica', DATEADD('DAY', 106, CURRENT_TIMESTAMP), TRUE, 13),
('Tech Women Summit', 'Mujeres en tecnologia e innovacion', DATEADD('DAY', 108, CURRENT_TIMESTAMP), TRUE, 3);

-- Bloque 3: Deportes (eventos 81-120)
INSERT INTO events (name, description, date, active, venue_id) VALUES
('Maraton Lima 42K', 'Maraton internacional de Lima', DATEADD('DAY', 14, CURRENT_TIMESTAMP), TRUE, 1),
('Torneo de Futbol Interbarrial', 'Competencia de futbol amateur', DATEADD('DAY', 17, CURRENT_TIMESTAMP), TRUE, 11),
('Carrera 10K San Isidro', 'Carrera de 10 kilometros', DATEADD('DAY', 23, CURRENT_TIMESTAMP), TRUE, 7),
('Campeonato de Surf', 'Torneo nacional de surf', DATEADD('DAY', 28, CURRENT_TIMESTAMP), TRUE, 9),
('Torneo de Voley Playa', 'Voley playa profesional', DATEADD('DAY', 33, CURRENT_TIMESTAMP), TRUE, 4),
('CrossFit Challenge', 'Competencia de CrossFit', DATEADD('DAY', 38, CURRENT_TIMESTAMP), TRUE, 6),
('Copa de Natacion', 'Torneo de natacion juvenil', DATEADD('DAY', 43, CURRENT_TIMESTAMP), TRUE, 9),
('Torneo de Tenis ATP', 'Tenis profesional masculino', DATEADD('DAY', 48, CURRENT_TIMESTAMP), TRUE, 6),
('Ultramaraton Andina', 'Carrera de ultrafondo en los Andes', DATEADD('DAY', 53, CURRENT_TIMESTAMP), TRUE, 1),
('Liga de Basquet', 'Basketball profesional peruano', DATEADD('DAY', 58, CURRENT_TIMESTAMP), TRUE, 6),
('Campeonato de Skate', 'Torneo nacional de skateboarding', DATEADD('DAY', 63, CURRENT_TIMESTAMP), TRUE, 14),
('Triatlon Lima', 'Triatlon olimpico en Lima', DATEADD('DAY', 68, CURRENT_TIMESTAMP), TRUE, 1),
('Copa de Futsal', 'Torneo de futbol sala', DATEADD('DAY', 73, CURRENT_TIMESTAMP), TRUE, 6),
('Mountain Bike Race', 'Carrera de ciclismo de montana', DATEADD('DAY', 78, CURRENT_TIMESTAMP), TRUE, 7),
('Torneo de Box', 'Velada de boxeo profesional', DATEADD('DAY', 83, CURRENT_TIMESTAMP), TRUE, 14),
('Open de Golf', 'Torneo abierto de golf', DATEADD('DAY', 88, CURRENT_TIMESTAMP), TRUE, 5),
('Judo Championship', 'Campeonato sudamericano de judo', DATEADD('DAY', 93, CURRENT_TIMESTAMP), TRUE, 6),
('Regata Velera', 'Regata internacional de veleros', DATEADD('DAY', 98, CURRENT_TIMESTAMP), TRUE, 9),
('Karate Open', 'Torneo abierto de karate', DATEADD('DAY', 103, CURRENT_TIMESTAMP), TRUE, 6),
('Trail Running Pachacamac', 'Carrera de trail en Pachacamac', DATEADD('DAY', 109, CURRENT_TIMESTAMP), TRUE, 12),
('Polo Cup', 'Copa de polo ecuestre', DATEADD('DAY', 111, CURRENT_TIMESTAMP), TRUE, 5),
('Escalada Indoor', 'Competencia de escalada deportiva', DATEADD('DAY', 113, CURRENT_TIMESTAMP), TRUE, 6),
('Torneo de Esgrima', 'Campeonato nacional de esgrima', DATEADD('DAY', 115, CURRENT_TIMESTAMP), TRUE, 6),
('BMX Racing', 'Carrera profesional de BMX', DATEADD('DAY', 117, CURRENT_TIMESTAMP), TRUE, 14),
('Paddle Tennis Open', 'Torneo abierto de padel', DATEADD('DAY', 119, CURRENT_TIMESTAMP), TRUE, 9),
('Wrestling Show', 'Espectaculo de lucha libre', DATEADD('DAY', 121, CURRENT_TIMESTAMP), TRUE, 14),
('Archery Tournament', 'Torneo de tiro con arco', DATEADD('DAY', 123, CURRENT_TIMESTAMP), TRUE, 5),
('E-Sports League', 'Liga de deportes electronicos', DATEADD('DAY', 125, CURRENT_TIMESTAMP), TRUE, 3),
('Yoga Marathon', 'Maraton de yoga al aire libre', DATEADD('DAY', 127, CURRENT_TIMESTAMP), TRUE, 5),
('Futbol Femenino Copa', 'Copa nacional de futbol femenino', DATEADD('DAY', 129, CURRENT_TIMESTAMP), TRUE, 11),
('Rowing Championship', 'Campeonato de remo', DATEADD('DAY', 131, CURRENT_TIMESTAMP), TRUE, 9),
('Handball Cup', 'Copa de balonmano nacional', DATEADD('DAY', 133, CURRENT_TIMESTAMP), TRUE, 6),
('Table Tennis Open', 'Torneo abierto de tenis de mesa', DATEADD('DAY', 135, CURRENT_TIMESTAMP), TRUE, 13),
('Cricket Festival', 'Festival de cricket en Lima', DATEADD('DAY', 137, CURRENT_TIMESTAMP), TRUE, 7),
('Gimnasia Ritmica', 'Competencia de gimnasia ritmica', DATEADD('DAY', 139, CURRENT_TIMESTAMP), TRUE, 8),
('Skateboard Night', 'Sesion nocturna de skate', DATEADD('DAY', 141, CURRENT_TIMESTAMP), TRUE, 14),
('Rugby Sevens', 'Torneo de rugby sevens', DATEADD('DAY', 143, CURRENT_TIMESTAMP), TRUE, 11),
('Badminton Open', 'Torneo abierto de badminton', DATEADD('DAY', 145, CURRENT_TIMESTAMP), TRUE, 6),
('Softball League', 'Liga de softball mixto', DATEADD('DAY', 147, CURRENT_TIMESTAMP), TRUE, 12),
('Danza Deportiva', 'Competencia de baile deportivo', DATEADD('DAY', 149, CURRENT_TIMESTAMP), TRUE, 8);

-- Bloque 4: Gastronomia (eventos 121-150)
INSERT INTO events (name, description, date, active, venue_id) VALUES
('Mistura 2026', 'Gran feria gastronomica del Peru', DATEADD('DAY', 20, CURRENT_TIMESTAMP), TRUE, 12),
('Festival del Ceviche', 'Degustacion de ceviches regionales', DATEADD('DAY', 30, CURRENT_TIMESTAMP), TRUE, 5),
('Wine & Cheese Night', 'Maridaje de vinos y quesos', DATEADD('DAY', 40, CURRENT_TIMESTAMP), TRUE, 15),
('Street Food Market', 'Mercado de comida callejera gourmet', DATEADD('DAY', 50, CURRENT_TIMESTAMP), TRUE, 7),
('Coffee Fest Lima', 'Festival del cafe peruano', DATEADD('DAY', 60, CURRENT_TIMESTAMP), TRUE, 5),
('Sushi Workshop', 'Taller de sushi japones', DATEADD('DAY', 70, CURRENT_TIMESTAMP), TRUE, 13),
('Chocolate Experience', 'Cata de chocolates peruanos', DATEADD('DAY', 80, CURRENT_TIMESTAMP), TRUE, 15),
('Craft Beer Festival', 'Festival de cervezas artesanales', DATEADD('DAY', 90, CURRENT_TIMESTAMP), TRUE, 12),
('Parrilla Fest', 'Festival de parrilla y asado', DATEADD('DAY', 100, CURRENT_TIMESTAMP), TRUE, 7),
('Cocina Nikkei', 'Taller de cocina nikkei peruana', DATEADD('DAY', 110, CURRENT_TIMESTAMP), TRUE, 13),
('Pisco Sour Day', 'Celebracion del Dia del Pisco Sour', DATEADD('DAY', 35, CURRENT_TIMESTAMP), TRUE, 15),
('Veggie Food Fair', 'Feria de comida vegana y vegetariana', DATEADD('DAY', 55, CURRENT_TIMESTAMP), TRUE, 5),
('Pasteleria Francesa', 'Taller de reposteria francesa', DATEADD('DAY', 75, CURRENT_TIMESTAMP), TRUE, 13),
('Comida Novoandina', 'Degustacion de cocina novoandina', DATEADD('DAY', 95, CURRENT_TIMESTAMP), TRUE, 15),
('Food Truck Rally', 'Rally de food trucks gourmet', DATEADD('DAY', 115, CURRENT_TIMESTAMP), TRUE, 7),
('Tea Ceremony', 'Ceremonia del te y maridaje', DATEADD('DAY', 120, CURRENT_TIMESTAMP), TRUE, 15),
('Cocina Marina', 'Mariscos y frutos del mar', DATEADD('DAY', 130, CURRENT_TIMESTAMP), TRUE, 9),
('Helado Artesanal Fest', 'Festival de helados artesanales', DATEADD('DAY', 140, CURRENT_TIMESTAMP), TRUE, 5),
('Pan Artesanal Workshop', 'Taller de panaderia artesanal', DATEADD('DAY', 150, CURRENT_TIMESTAMP), TRUE, 13),
('Brunch Festival', 'Festival de brunch gourmet', DATEADD('DAY', 160, CURRENT_TIMESTAMP), TRUE, 15),
('Cocina Selvática', 'Sabores de la Amazonia peruana', DATEADD('DAY', 170, CURRENT_TIMESTAMP), TRUE, 12),
('Mixologia Creativa', 'Taller de cocteleria creativa', DATEADD('DAY', 180, CURRENT_TIMESTAMP), TRUE, 15),
('Ramen Night', 'Noche de ramen japones', DATEADD('DAY', 190, CURRENT_TIMESTAMP), TRUE, 13),
('Festival del Tamal', 'Degustacion de tamales regionales', DATEADD('DAY', 200, CURRENT_TIMESTAMP), TRUE, 7),
('Olive Oil Tasting', 'Cata de aceites de oliva premium', DATEADD('DAY', 210, CURRENT_TIMESTAMP), TRUE, 15),
('BBQ Championship', 'Campeonato de BBQ americano', DATEADD('DAY', 220, CURRENT_TIMESTAMP), TRUE, 12),
('Dim Sum Workshop', 'Taller de dim sum cantonés', DATEADD('DAY', 230, CURRENT_TIMESTAMP), TRUE, 13),
('Cacao Festival', 'Festival del cacao y derivados', DATEADD('DAY', 240, CURRENT_TIMESTAMP), TRUE, 5),
('Empanada Fest', 'Festival de empanadas sudamericanas', DATEADD('DAY', 250, CURRENT_TIMESTAMP), TRUE, 7),
('Sake Tasting Night', 'Degustacion de sake japones', DATEADD('DAY', 260, CURRENT_TIMESTAMP), TRUE, 15);

-- Bloque 5: Teatro y Arte (eventos 151-180)
INSERT INTO events (name, description, date, active, venue_id) VALUES
('Hamlet - Shakespeare', 'Clasico del teatro universal', DATEADD('DAY', 11, CURRENT_TIMESTAMP), TRUE, 2),
('Romeo y Julieta', 'Tragedia romantica de Shakespeare', DATEADD('DAY', 18, CURRENT_TIMESTAMP), TRUE, 8),
('Don Quijote Musical', 'Musical basado en Cervantes', DATEADD('DAY', 25, CURRENT_TIMESTAMP), TRUE, 2),
('El Fantasma de la Opera', 'Clasico del teatro musical', DATEADD('DAY', 32, CURRENT_TIMESTAMP), TRUE, 8),
('Improv Comedy Night', 'Comedia de improvisacion', DATEADD('DAY', 39, CURRENT_TIMESTAMP), TRUE, 10),
('Danza Contemporanea', 'Espectaculo de danza moderna', DATEADD('DAY', 46, CURRENT_TIMESTAMP), TRUE, 8),
('Circo del Sol Tributo', 'Espectaculo acrobatico y artistico', DATEADD('DAY', 53, CURRENT_TIMESTAMP), TRUE, 6),
('Monologos de Humor', 'Stand-up comedy en espanol', DATEADD('DAY', 60, CURRENT_TIMESTAMP), TRUE, 10),
('Ballet Clasico', 'El Lago de los Cisnes', DATEADD('DAY', 67, CURRENT_TIMESTAMP), TRUE, 8),
('Teatro de Sombras', 'Espectaculo de sombras chinescas', DATEADD('DAY', 74, CURRENT_TIMESTAMP), TRUE, 10),
('Musical Kids', 'Teatro musical para toda la familia', DATEADD('DAY', 81, CURRENT_TIMESTAMP), TRUE, 2),
('Flamenco Passion', 'Espectaculo de flamenco y guitarra', DATEADD('DAY', 88, CURRENT_TIMESTAMP), TRUE, 8),
('Marionetas del Mundo', 'Festival de titeres internacionales', DATEADD('DAY', 95, CURRENT_TIMESTAMP), TRUE, 10),
('Cabaret Nocturno', 'Show de cabaret y variedades', DATEADD('DAY', 102, CURRENT_TIMESTAMP), TRUE, 2),
('Danza Folklorica', 'Danzas tradicionales del Peru', DATEADD('DAY', 109, CURRENT_TIMESTAMP), TRUE, 7),
('Cuentacuentos', 'Festival de narracion oral', DATEADD('DAY', 116, CURRENT_TIMESTAMP), TRUE, 10),
('Circo Acrobatico', 'Acrobacias aereas y malabares', DATEADD('DAY', 123, CURRENT_TIMESTAMP), TRUE, 6),
('Mimo en la Plaza', 'Espectaculo de mimo y pantomima', DATEADD('DAY', 130, CURRENT_TIMESTAMP), TRUE, 7),
('Teatro Negro', 'Teatro negro de Praga', DATEADD('DAY', 137, CURRENT_TIMESTAMP), TRUE, 2),
('Tap Dance Show', 'Espectaculo de claque americano', DATEADD('DAY', 144, CURRENT_TIMESTAMP), TRUE, 8),
('La Casa de Bernarda Alba', 'Drama de Federico Garcia Lorca', DATEADD('DAY', 151, CURRENT_TIMESTAMP), TRUE, 2),
('Commedia dellArte', 'Teatro de mascaras italiano', DATEADD('DAY', 158, CURRENT_TIMESTAMP), TRUE, 10),
('Butoh Performance', 'Danza japonesa contemporanea', DATEADD('DAY', 165, CURRENT_TIMESTAMP), TRUE, 15),
('Magia en Escena', 'Show de ilusionismo profesional', DATEADD('DAY', 172, CURRENT_TIMESTAMP), TRUE, 2),
('Zarzuela Espanola', 'Genero lirico espanol', DATEADD('DAY', 179, CURRENT_TIMESTAMP), TRUE, 8),
('Performance Art', 'Arte performatico contemporaneo', DATEADD('DAY', 186, CURRENT_TIMESTAMP), TRUE, 10),
('Tango Show', 'Espectaculo de tango argentino', DATEADD('DAY', 193, CURRENT_TIMESTAMP), TRUE, 2),
('Kathakali Dance', 'Danza clasica de India', DATEADD('DAY', 200, CURRENT_TIMESTAMP), TRUE, 8),
('Spoken Word Night', 'Poesia hablada y performance', DATEADD('DAY', 207, CURRENT_TIMESTAMP), TRUE, 10),
('Noche de Magia Urbana', 'Magia callejera profesional', DATEADD('DAY', 214, CURRENT_TIMESTAMP), TRUE, 15);

-- Bloque 6: Festivales y Mixtos (eventos 181-200)
INSERT INTO events (name, description, date, active, venue_id) VALUES
('Festival de Primavera', 'Gran festival de primavera multiactividad', DATEADD('DAY', 15, CURRENT_TIMESTAMP), TRUE, 7),
('Noche de Museos', 'Recorrido nocturno por museos de Lima', DATEADD('DAY', 22, CURRENT_TIMESTAMP), TRUE, 15),
('Festival del Libro', 'Feria internacional del libro de Lima', DATEADD('DAY', 29, CURRENT_TIMESTAMP), TRUE, 3),
('Comic Con Lima', 'Convencion de comics y cultura pop', DATEADD('DAY', 36, CURRENT_TIMESTAMP), TRUE, 12),
('Festival de Cine', 'Muestra de cine independiente', DATEADD('DAY', 43, CURRENT_TIMESTAMP), TRUE, 10),
('Dia de la Tierra', 'Actividades ecologicas y reciclaje', DATEADD('DAY', 50, CURRENT_TIMESTAMP), TRUE, 5),
('Festival Multicultural', 'Celebracion de la diversidad cultural', DATEADD('DAY', 57, CURRENT_TIMESTAMP), TRUE, 7),
('Mercado de Pulgas Vintage', 'Feria de objetos vintage y retro', DATEADD('DAY', 64, CURRENT_TIMESTAMP), TRUE, 12),
('Festival de Flores', 'Exposicion y venta de flores', DATEADD('DAY', 71, CURRENT_TIMESTAMP), TRUE, 5),
('Anime Fest Lima', 'Convencion de anime y manga', DATEADD('DAY', 78, CURRENT_TIMESTAMP), TRUE, 3),
('Dia del Nino', 'Actividades para toda la familia', DATEADD('DAY', 85, CURRENT_TIMESTAMP), TRUE, 7),
('Festival de Arte Urbano', 'Graffiti, murales y arte callejero', DATEADD('DAY', 92, CURRENT_TIMESTAMP), TRUE, 14),
('Feria de Mascotas', 'Evento para amantes de los animales', DATEADD('DAY', 99, CURRENT_TIMESTAMP), TRUE, 12),
('Festival de la Cerveza', 'Oktoberfest limeno', DATEADD('DAY', 106, CURRENT_TIMESTAMP), TRUE, 4),
('Hackathon Lima', 'Maraton de programacion de 48 horas', DATEADD('DAY', 113, CURRENT_TIMESTAMP), TRUE, 3),
('Festival Steampunk', 'Cultura y estetica steampunk', DATEADD('DAY', 120, CURRENT_TIMESTAMP), TRUE, 12),
('Carnaval de Lima', 'Gran carnaval con desfiles y musica', DATEADD('DAY', 127, CURRENT_TIMESTAMP), TRUE, 7),
('Festival de Drones', 'Exhibicion y competencia de drones', DATEADD('DAY', 134, CURRENT_TIMESTAMP), TRUE, 14),
('Feria Ecologica', 'Productos organicos y sostenibles', DATEADD('DAY', 141, CURRENT_TIMESTAMP), TRUE, 5),
('Fiesta de Fin de Ano', 'Gran celebracion de fin de ano', DATEADD('DAY', 148, CURRENT_TIMESTAMP), TRUE, 1);

-- ==================== EVENTS_CATEGORIES (asignaciones variadas) ====================
-- Conciertos y Festivales (eventos 1-40)
INSERT INTO events_categories (event_id, category_id) VALUES
(1, 1), (1, 6), (2, 1), (3, 1), (3, 6), (4, 1), (4, 6),
(5, 1), (6, 1), (6, 7), (7, 1), (8, 1), (9, 1), (9, 6),
(10, 1), (11, 1), (11, 6), (12, 1), (13, 1), (14, 1), (14, 6),
(15, 1), (15, 6), (16, 1), (17, 1), (18, 1), (19, 1), (20, 1),
(21, 1), (22, 1), (22, 6), (23, 1), (23, 7), (24, 1), (24, 6),
(25, 1), (26, 1), (26, 6), (27, 1), (27, 6), (28, 1),
(29, 1), (30, 1), (30, 6), (31, 1), (31, 7), (32, 1),
(33, 1), (33, 6), (34, 1), (35, 1), (36, 1), (37, 1), (37, 6),
(38, 1), (38, 7), (39, 1), (40, 1), (40, 6);

-- Conferencias y Talleres (eventos 41-80)
INSERT INTO events_categories (event_id, category_id) VALUES
(41, 3), (42, 2), (42, 3), (43, 3), (44, 2), (45, 3),
(46, 2), (47, 3), (48, 3), (49, 2), (50, 3), (50, 2),
(51, 2), (52, 2), (53, 3), (54, 2), (55, 3), (56, 2), (56, 3),
(57, 3), (58, 2), (59, 3), (60, 2), (61, 3), (62, 2),
(63, 3), (64, 2), (65, 3), (66, 2), (67, 3), (68, 2),
(69, 3), (70, 2), (71, 3), (72, 2), (73, 3), (73, 2),
(74, 2), (75, 3), (76, 2), (77, 3), (78, 3), (79, 2),
(80, 3);

-- Deportes (eventos 81-120)
INSERT INTO events_categories (event_id, category_id) VALUES
(81, 4), (82, 4), (83, 4), (84, 4), (85, 4), (86, 4),
(87, 4), (88, 4), (89, 4), (90, 4), (91, 4), (92, 4),
(93, 4), (94, 4), (95, 4), (96, 4), (97, 4), (98, 4),
(99, 4), (100, 4), (101, 4), (102, 4), (103, 4), (104, 4),
(105, 4), (106, 4), (107, 4), (108, 4), (109, 4), (110, 4),
(111, 4), (112, 4), (113, 4), (114, 4), (115, 4), (116, 4),
(117, 4), (118, 4), (119, 4), (120, 4);

-- Gastronomia (eventos 121-150)
INSERT INTO events_categories (event_id, category_id) VALUES
(121, 5), (121, 6), (122, 5), (123, 5), (124, 5), (124, 6),
(125, 5), (126, 5), (126, 2), (127, 5), (128, 5), (128, 6),
(129, 5), (130, 5), (130, 2), (131, 5), (132, 5), (133, 5), (133, 2),
(134, 5), (135, 5), (135, 6), (136, 5), (137, 5), (138, 5),
(139, 5), (139, 2), (140, 5), (141, 5), (141, 6), (142, 5),
(143, 5), (143, 2), (144, 5), (145, 5), (146, 5), (147, 5), (147, 6),
(148, 5), (148, 2), (149, 5), (150, 5);

-- Teatro y Arte (eventos 151-180)
INSERT INTO events_categories (event_id, category_id) VALUES
(151, 7), (152, 7), (153, 7), (153, 1), (154, 7), (154, 1),
(155, 7), (156, 7), (157, 7), (157, 6), (158, 7), (159, 7),
(160, 7), (161, 7), (162, 7), (162, 1), (163, 7), (164, 7),
(165, 7), (165, 6), (166, 7), (167, 7), (167, 6), (168, 7),
(169, 7), (170, 7), (171, 7), (172, 7), (173, 7), (174, 7),
(175, 7), (176, 7), (177, 7), (177, 1), (178, 7), (179, 7),
(180, 7);

-- Festivales y Mixtos (eventos 181-200)
INSERT INTO events_categories (event_id, category_id) VALUES
(181, 6), (181, 1), (182, 6), (182, 7), (183, 6), (184, 6),
(185, 6), (185, 7), (186, 6), (187, 6), (187, 1), (188, 6),
(189, 6), (190, 6), (191, 6), (192, 6), (192, 7),
(193, 6), (194, 6), (194, 5), (195, 3), (195, 2),
(196, 6), (197, 6), (197, 1), (198, 6), (198, 3),
(199, 6), (200, 6), (200, 1);
