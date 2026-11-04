-- ===================================================================
-- CATÁLOGO DE PROVINCIAS (Andalucía + Principales)
-- ===================================================================
INSERT IGNORE INTO provincias (id, codigo, nombre) VALUES
                                                (1, '18', 'Granada'),
                                                (2, '29', 'Málaga'),
                                                (3, '41', 'Sevilla'),
                                                (4, '04', 'Almería'),
                                                (5, '11', 'Cádiz'),
                                                (6, '14', 'Córdoba'),
                                                (7, '21', 'Huelva'),
                                                (8, '23', 'Jaén'),
                                                (9, '28', 'Madrid'),
                                                (10, '08', 'Barcelona');

-- ===================================================================
-- CATÁLOGO DE MUNICIPIOS (Provincia de Granada - provincia_id = 1)
-- ===================================================================
INSERT IGNORE INTO municipios (id, nombre, codigo_postal, provincia_id) VALUES
                                                                     (1, 'Granada', '18001', 1),
                                                                     (2, 'Albolote', '18220', 1),
                                                                     (3, 'Armilla', '18100', 1),
                                                                     (4, 'Motril', '18600', 1),
                                                                     (5, 'Maracena', '18200', 1),
                                                                     (6, 'Baza', '18800', 1),
                                                                     (7, 'Loja', '18300', 1),
                                                                     (8, 'Santa Fe', '18320', 1),
                                                                     (9, 'Atarfe', '18230', 1),
                                                                     (10, 'Churriana de la Vega', '18101', 1),
                                                                     (11, 'Las Gabias', '18110', 1),
                                                                     (12, 'Ogíjares', '18151', 1),
                                                                     (13, 'La Zubia', '18140', 1),
                                                                     (14, 'Almuñécar', '18690', 1),
                                                                     (15, 'Guadix', '18500', 1);

-- ===================================================================
-- CLIENTES DE PRUEBA
-- ===================================================================
INSERT IGNORE INTO clientes (id, razon_social, cif, persona_contacto, email, telefono, direccion_calle, municipio_id) VALUES
    (1, 'Ayuntamiento de Granada', 'P1808700J', 'Concejalía de Festejos', 'festejos@granada.org', '958248100', 'Plaza del Carmen, 5', 1),
    (2, 'Ayuntamiento de Albolote', 'P1800300G', 'Área de Cultura y Fiestas', 'cultura@albolote.gov.es', '958465111', 'Plaza de España, 1', 2),
    (3, 'Centro Comercial Nevada Shopping', 'B18987654', 'Gerencia de Eventos', 'gerencia@nevadashopping.com', '958555000', 'Avda. de las Palmeras, 75', 3),
    (4, 'Real Sociedad de Tenis de Granada', 'G18012345', 'Eventos Sociales', 'eventos@rstgranada.com', '958121580', 'Calle Aconcagua, s/n', 1);

-- ===================================================================
-- EVENTOS Y PROYECTOS
-- ===================================================================
INSERT IGNORE INTO eventos (id, nombre_evento, fecha_inicio, fecha_fin, presupuesto, estado, ubicacion, cliente_id) VALUES
    (1, 'Cabalgata de Reyes 2027', '2027-01-05', '2027-01-06', 45000.00, 'CONFIRMADO', 'Centro Histórico y Gran Vía, Granada', 1),
    (2, 'Fiestas Populares y Conciertos de Verano', '2026-07-15', '2026-07-19', 28500.00, 'EN_PROCESO', 'Plaza de España, Albolote', 2),
    (3, 'Campaña Navideña y Pista de Hielo', '2026-12-01', '2027-01-10', 60000.00, 'PRESUPUESTADO', 'Plaza Central Nevada Shopping, Armilla', 3),
    (4, 'Torneo Nacional de Tenis Ciudad de la Alhambra', '2026-09-10', '2026-09-17', 15000.00, 'CONFIRMADO', 'Pistas Centrales RST Granada', 4),
    (5, 'Festival Internacional de Música y Danza', '2026-06-20', '2026-07-05', 75000.00, 'FINALIZADO', 'Jardines del Generalife, Granada', 1);

-- ===================================================================
-- CATÁLOGO DE STOCK, EQUIPO TÉCNICO, PERSONAL Y ANIMADORES
-- ===================================================================
INSERT IGNORE INTO recursos_stock (id, codigo, nombre, categoria, descripcion, cantidad_total, coste_unitario, estado, ubicacion_almacen) VALUES
    (1, 'SND-001', 'Sistema Line Array JBL VTX A8 (Pareja + Subgraves)', 'SONIDO', 'Equipo de sonido profesional de alta potencia para recintos exteriores y conciertos (8.000W RMS)', 6, 450.00, 'DISPONIBLE', 'Nave Central - Zona Audio A1'),
    (2, 'SND-002', 'Mesa de Mezclas Digital Yamaha CL5 + Río 3224', 'SONIDO', 'Consola digital de 72 canales con rack de escenario Dante redundant', 3, 280.00, 'DISPONIBLE', 'Nave Central - Zona Audio A2'),
    (3, 'ILU-001', 'Cabeza Móvil Beam/Spot Robe Pointe 280W', 'ILUMINACION', 'Foco robotizado híbrido Beam/Spot/Wash con flightcase doble', 24, 65.00, 'DISPONIBLE', 'Nave Central - Pasillo Iluminación B1'),
    (4, 'ILU-002', 'Máquina de Humo y Efectos CO2 Chauvet', 'ILUMINACION', 'Generador de efectos especiales DMX de alto caudal para escenarios', 4, 85.00, 'EN_MANTENIMIENTO', 'Taller Técnico - Revisión'),
    (5, 'EQT-001', 'Pantalla LED Gigante Outdoor P3.9 (Módulos 4x3m)', 'EQUIPO_TECNICO', 'Pantalla LED de exterior alta luminosidad con procesador Novastar 4K', 4, 650.00, 'DISPONIBLE', 'Nave Central - Zona Vídeo C1'),
    (6, 'EQT-002', 'Generador Electrógeno Insonorizado 100 kVA', 'EQUIPO_TECNICO', 'Grupo electrógeno autónomo estabilizado con cuadro de distribución trifásico', 3, 520.00, 'DISPONIBLE', 'Patio Logístico Exterior'),
    (7, 'EST-001', 'Escenario Modular Layher 12x10m Cubierto', 'ESTRUCTURAS', 'Escenario homologado con techo orbit, escaleras, barandillas y rampa de carga', 2, 1200.00, 'DISPONIBLE', 'Nave Auxiliar - Estructuras'),
    (8, 'PER-001', 'Técnico Superior de Sonido Directo (FOH / Monitores)', 'PERSONAL_TECNICO', 'Ingeniero de sonido titulado para ajuste de sistemas, mezcla en vivo y microfonía RF', 5, 220.00, 'DISPONIBLE', 'Plantilla Técnica Interna'),
    (9, 'PER-002', 'Operador de Iluminación DMX y Vídeo (GrandMA)', 'PERSONAL_TECNICO', 'Técnico especialista en programación de consolas de luces y servidores de vídeo', 4, 210.00, 'DISPONIBLE', 'Plantilla Técnica Interna'),
    (10, 'PER-003', 'Rigger / Montador de Estructuras y Escenarios', 'PERSONAL_TECNICO', 'Personal técnico certificado en trabajos en altura, montaje de truss y carga/descarga', 12, 150.00, 'DISPONIBLE', 'Plantilla Técnica Interna'),
    (11, 'ANI-001', 'Animadores Infantiles, Pasacalles y Personajes', 'ANIMADORES', 'Equipo de animación sociocultural con vestuario temático, juegos, globoflexia y bailes', 15, 130.00, 'DISPONIBLE', 'Bolsa de Animadores y Artistas'),
    (12, 'ANI-002', 'Speaker / Presentador y DJ Animador de Eventos', 'ANIMADORES', 'Locutor profesional y DJ animador para galas, entregas de premios y fiestas populares', 4, 250.00, 'DISPONIBLE', 'Bolsa de Animadores y Artistas');

-- ===================================================================
-- ASIGNACIONES DE STOCK Y PERSONAL A EVENTOS
-- ===================================================================
INSERT IGNORE INTO asignaciones_evento (id, evento_id, recurso_id, cantidad, fecha_asignacion, estado_asignacion, responsable, observaciones) VALUES
    (1, 1, 1, 2, '2026-11-01', 'RESERVADO', 'Carlos Molina (Jefe Sonido)', 'Sonorización carrozas principales y Plaza del Carmen'),
    (2, 1, 11, 8, '2026-11-01', 'RESERVADO', 'Laura Gómez (Coord. Animación)', 'Pasacalles real y animación infantil durante todo el recorrido'),
    (3, 2, 7, 1, '2026-07-10', 'EN_USO', 'Miguel Ángel Ruiz', 'Montaje en Plaza de España 48h antes del inicio'),
    (4, 2, 3, 12, '2026-07-10', 'EN_USO', 'Elena Sánchez (Iluminación)', 'Puente trasero y frontal del escenario principal'),
    (5, 2, 8, 2, '2026-07-10', 'EN_USO', 'Carlos Molina (Jefe Sonido)', 'Un técnico en FOH y un técnico en monitores de escenario'),
    (6, 2, 12, 1, '2026-07-10', 'EN_USO', 'Laura Gómez (Coord. Animación)', 'DJ y speaker para cierre de conciertos nocturnos');
