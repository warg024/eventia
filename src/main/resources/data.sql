-- ===================================================================
-- 1. CATÁLOGO DE PROVINCIAS (Andalucía + Principales)
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
-- 2. CATÁLOGO DE MUNICIPIOS (Todas las provincias registradas)
-- ===================================================================
INSERT IGNORE INTO municipios (id, nombre, codigo_postal, provincia_id) VALUES
    -- Granada (provincia_id = 1)
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
    (15, 'Guadix', '18500', 1),
    -- Málaga (provincia_id = 2)
    (16, 'Málaga', '29001', 2),
    (17, 'Marbella', '29601', 2),
    (18, 'Antequera', '29200', 2),
    (19, 'Fuengirola', '29640', 2),
    -- Sevilla (provincia_id = 3)
    (20, 'Sevilla', '41001', 3),
    (21, 'Dos Hermanas', '41701', 3),
    (22, 'Mairena del Aljarafe', '41927', 3),
    (23, 'Alcalá de Guadaíra', '41500', 3),
    -- Almería (provincia_id = 4)
    (24, 'Almería', '04001', 4),
    (25, 'Roquetas de Mar', '04740', 4),
    (26, 'El Ejido', '04700', 4),
    -- Cádiz (provincia_id = 5)
    (27, 'Cádiz', '11001', 5),
    (28, 'Jerez de la Frontera', '11401', 5),
    (29, 'El Puerto de Santa María', '11500', 5),
    -- Córdoba (provincia_id = 6)
    (30, 'Córdoba', '14001', 6),
    (31, 'Lucena', '14900', 6),
    (32, 'Puente Genil', '14500', 6),
    -- Huelva (provincia_id = 7)
    (33, 'Huelva', '21001', 7),
    (34, 'Lepe', '21440', 7),
    (35, 'Almonte', '21730', 7),
    -- Jaén (provincia_id = 8)
    (36, 'Jaén', '23001', 8),
    (37, 'Úbeda', '23400', 8),
    (38, 'Linares', '23700', 8),
    -- Madrid (provincia_id = 9)
    (39, 'Madrid', '28001', 9),
    (40, 'Alcobendas', '28100', 9),
    -- Barcelona (provincia_id = 10)
    (41, 'Barcelona', '08001', 10),
    (42, 'Hospitalet de Llobregat', '08901', 10);

-- ===================================================================
-- 3. CARTERA DE CLIENTES (Instituciones Públicas y Empresas)
-- ===================================================================
INSERT IGNORE INTO clientes (id, razon_social, cif, persona_contacto, email, telefono, direccion_calle, municipio_id) VALUES
    (1, 'Ayuntamiento de Granada', 'P1808700J', 'Concejalía de Festejos', 'festejos@granada.org', '958248100', 'Plaza del Carmen, 5', 1),
    (2, 'Ayuntamiento de Albolote', 'P1800300G', 'Área de Cultura y Fiestas', 'cultura@albolote.gov.es', '958465111', 'Plaza de España, 1', 2),
    (3, 'Centro Comercial Nevada Shopping', 'B18987654', 'Gerencia de Eventos', 'gerencia@nevadashopping.com', '958555000', 'Avda. de las Palmeras, 75', 3),
    (4, 'Real Sociedad de Tenis de Granada', 'G18012345', 'Eventos Sociales', 'eventos@rstgranada.com', '958121580', 'Calle Aconcagua, s/n', 1),
    (5, 'Palacio de Congresos de Granada', 'A18223344', 'Dirección de Producción y Ferias', 'produccion@pcgr.org', '958246700', 'Paseo del Violón, s/n', 1),
    (6, 'Universidad de Granada (UGR)', 'Q1818002F', 'Vicerrectorado de Extensión Universitaria', 'cultura@ugr.es', '958243000', 'Cuesta del Hospicio, s/n', 1),
    (7, 'Ayuntamiento de Motril', 'P1814200B', 'Área de Eventos y Playas', 'festejos@motril.es', '958838300', 'Plaza España, 1', 4),
    (8, 'Ayuntamiento de Málaga', 'P2906700F', 'Delegación de Fiestas y Grandes Eventos', 'fiestas@malaga.eu', '951926010', 'Avda. de Cervantes, 4', 16),
    (9, 'Grupo Musical Andalucía Live S.L.', 'B41998877', 'Roberto Díaz (Promotor)', 'contratacion@andalucialive.es', '954321900', 'Avda. de la Constitución, 24', 20),
    (10, 'Hotel Alhambra Palace Eventos', 'A18001122', 'Carmen Ruiz (Directora MICE)', 'mice@h-alhambrapalace.es', '958221468', 'Plaza Arquitecto García de Paredes, 1', 1),
    (11, 'Diputación Provincial de Jaén', 'P2300000C', 'Área de Cultura y Deportes', 'cultura@dipujaen.es', '953248000', 'Plaza de San Francisco, 2', 36),
    (12, 'Recinto Ferial IFEMA Eventos Sur', 'Q2801999H', 'Javier Morales (Coord. Pabellones)', 'operaciones@ifema-sur.es', '917223000', 'Avda. del Partenón, 5', 39);

-- ===================================================================
-- 4. PROGRAMACIÓN DE EVENTOS Y PROYECTOS (Todos los estados)
-- ===================================================================
INSERT IGNORE INTO eventos (id, nombre_evento, fecha_inicio, fecha_fin, presupuesto, estado, ubicacion, cliente_id) VALUES
    (1, 'Cabalgata de Reyes 2027', '2027-01-05', '2027-01-06', 45000.00, 'CONFIRMADO', 'Centro Histórico y Gran Vía, Granada', 1),
    (2, 'Fiestas Populares y Conciertos de Verano', '2026-07-15', '2026-07-19', 28500.00, 'EN_PROCESO', 'Plaza de España y Recinto Ferial, Albolote', 2),
    (3, 'Campaña Navideña y Pista de Hielo', '2026-12-01', '2027-01-10', 60000.00, 'PRESUPUESTADO', 'Plaza Central Nevada Shopping, Armilla', 3),
    (4, 'Torneo Nacional de Tenis Ciudad de la Alhambra', '2026-09-10', '2026-09-17', 15000.00, 'CONFIRMADO', 'Pistas Centrales RST Granada', 4),
    (5, 'Festival Internacional de Música y Danza', '2026-06-20', '2026-07-05', 75000.00, 'FINALIZADO', 'Jardines del Generalife, Granada', 1),
    (6, 'Congreso Andaluz de Innovación y Tecnología', '2026-11-18', '2026-11-20', 34200.00, 'EN_PROCESO', 'Auditorio García Lorca - Palacio de Congresos, Granada', 5),
    (7, 'Gala de Bienvenida Universitaria UGR 2026', '2026-10-08', '2026-10-09', 19800.00, 'FINALIZADO', 'Paseíllos Universitarios Fuentenueva, Granada', 6),
    (8, 'Festival Costa Tropical Sound Motril', '2026-08-12', '2026-08-15', 52000.00, 'CONFIRMADO', 'Playa de Poniente - Recinto Conciertos, Motril', 7),
    (9, 'Feria Gastronómica y Escenario Flamenco Málaga', '2026-11-05', '2026-11-08', 38900.00, 'EN_PROCESO', 'Muelle Uno - Puerto de Málaga', 8),
    (10, 'Gira Indie Sur - Concierto Fin de Temporada', '2026-12-18', '2026-12-19', 41500.00, 'PRESUPUESTADO', 'Auditorio Rocío Jurado, Sevilla', 9),
    (11, 'Cena de Gala Anual y Entrega de Premios Empresariales', '2026-11-27', '2026-11-28', 12400.00, 'CONFIRMADO', 'Salón Árabe - Hotel Alhambra Palace, Granada', 10),
    (12, 'Ciclo Cultural Noches en los Castillos de Jaén', '2026-07-24', '2026-07-26', 22600.00, 'FINALIZADO', 'Plaza de Vázquez de Molina, Úbeda', 11);

-- ===================================================================
-- 5. CATÁLOGO DE STOCK, EQUIPO TÉCNICO, PERSONAL Y ANIMADORES
--    Cubre las 7 categorías (SONIDO, ILUMINACION, EQUIPO_TECNICO,
--    ESTRUCTURAS, PERSONAL_TECNICO, ANIMADORES, OTROS) y los 3 estados
--    (DISPONIBLE, EN_MANTENIMIENTO, BAJA)
-- ===================================================================
INSERT INTO recursos_stock (id, codigo, nombre, categoria, descripcion, cantidad_total, coste_unitario, estado, ubicacion_almacen) VALUES
    -- SONIDO
    (1, 'SND-001', 'Sistema Line Array JBL VTX A8 (Pareja + Subgraves)', 'SONIDO', 'Equipo de sonido profesional de alta potencia para recintos exteriores y conciertos (8.000W RMS)', 8, 450.00, 'DISPONIBLE', 'Nave Central - Zona Audio A1'),
    (2, 'SND-002', 'Mesa de Mezclas Digital Yamaha CL5 + Río 3224', 'SONIDO', 'Consola digital de 72 canales con rack de escenario Dante redundante', 4, 280.00, 'DISPONIBLE', 'Nave Central - Zona Audio A2'),
    (13, 'SND-003', 'Kit Microfonía Inalámbrica Shure Axient Digital (8 Canales)', 'SONIDO', 'Rack de 8 receptores digitales RF con 6 micrófonos de mano Beta 58A y 4 petacas de diadema', 6, 190.00, 'DISPONIBLE', 'Nave Central - Sala RF Audio'),
    (14, 'SND-004', 'Monitor de Escenario Activo d&b M4 Wedge 1200W', 'SONIDO', 'Cuña coaxial de monitoraje profesional para músicos y artistas en directo', 16, 75.00, 'DISPONIBLE', 'Nave Central - Zona Audio A3'),
    (15, 'SND-005', 'Etapa de Potencia Crown I-Tech 12000HD (Rack Antiguo)', 'SONIDO', 'Amplificador de gira retirado del servicio activo por renovación a serie VTX autoamplificada', 2, 95.00, 'BAJA', 'Almacén Pasivo - Retirados'),
    -- ILUMINACION
    (3, 'ILU-001', 'Cabeza Móvil Beam/Spot Robe Pointe 280W', 'ILUMINACION', 'Foco robotizado híbrido Beam/Spot/Wash con flightcase doble', 32, 65.00, 'DISPONIBLE', 'Nave Central - Pasillo Iluminación B1'),
    (4, 'ILU-002', 'Máquina de Humo y Efectos CO2 Chauvet', 'ILUMINACION', 'Generador de efectos especiales DMX de alto caudal para escenarios', 4, 85.00, 'EN_MANTENIMIENTO', 'Taller Técnico - Revisión'),
    (16, 'ILU-003', 'Foco Recorte LED ETC Source Four Lustr Series 2', 'ILUMINACION', 'Proyector elipsoidal LED RGBW para teatros, galas institucionales, congresos y televisión', 20, 48.00, 'DISPONIBLE', 'Nave Central - Pasillo Iluminación B2'),
    (17, 'ILU-004', 'Cegadora LED Exterior 4-Lite IP65 Cálido/Ámbar', 'ILUMINACION', 'Blinder de público resistente a intemperie para festivales y conciertos al aire libre', 12, 55.00, 'DISPONIBLE', 'Nave Central - Pasillo Iluminación B3'),
    (18, 'ILU-005', 'Cañón de Seguimiento Robert Juliat Aramis 2500W HMI', 'ILUMINACION', 'Followspot de largo alcance con trípode y ballast electrónico (en revisión de lámpara HMI)', 2, 160.00, 'EN_MANTENIMIENTO', 'Taller Técnico - Bancada Óptica'),
    -- EQUIPO_TECNICO
    (5, 'EQT-001', 'Pantalla LED Gigante Outdoor P3.9 (Módulos 4x3m)', 'EQUIPO_TECNICO', 'Pantalla LED de exterior alta luminosidad con procesador Novastar 4K', 6, 650.00, 'DISPONIBLE', 'Nave Central - Zona Vídeo C1'),
    (6, 'EQT-002', 'Generador Electrógeno Insonorizado 100 kVA', 'EQUIPO_TECNICO', 'Grupo electrógeno autónomo estabilizado con cuadro de distribución trifásico', 4, 520.00, 'DISPONIBLE', 'Patio Logístico Exterior'),
    (19, 'EQT-003', 'Sistema Realización Multicámara Blackmagic ATEM 4K + 3 Cámaras SDI', 'EQUIPO_TECNICO', 'Control de realización en vivo para streaming, IMAG en pantallas gigantes y grabación de congresos', 3, 420.00, 'DISPONIBLE', 'Nave Central - Zona Vídeo C2'),
    (20, 'EQT-004', 'Proyector Láser Panasonic PT-RZ970 10.000 Lúmenes WUXGA', 'EQUIPO_TECNICO', 'Proyector láser de óptica intercambiable para auditorios y mapping arquitectónico', 4, 310.00, 'DISPONIBLE', 'Nave Central - Zona Vídeo C3'),
    -- ESTRUCTURAS
    (7, 'EST-001', 'Escenario Modular Layher 12x10m Cubierto', 'ESTRUCTURAS', 'Escenario homologado con techo orbit, escaleras, barandillas y rampa de carga', 3, 1200.00, 'DISPONIBLE', 'Nave Auxiliar - Estructuras'),
    (21, 'EST-002', 'Puente Truss Cuadrado Prolyte H30V (Tramos 12m + 2 Torres Elevadoras)', 'ESTRUCTURAS', 'Estructura de aluminio certificada con torres Guil ELC-780 para colgado de iluminación y PA', 6, 340.00, 'DISPONIBLE', 'Nave Auxiliar - Zona Truss'),
    (22, 'EST-003', 'Carpa Plegable Hexagonal Eventia Pro 6x3m Ignífuga M2', 'ESTRUCTURAS', 'Carpa impermeable para control FOH, camerinos rápidos, acreditaciones y stands de feria', 10, 110.00, 'DISPONIBLE', 'Nave Auxiliar - Carpas y Lonas'),
    (23, 'EST-004', 'Lote 50 Vallas Antiavalancha Homologadas Acero Galvanizado', 'ESTRUCTURAS', 'Vallado perimetral de seguridad con plataforma antideslizante para foso de conciertos y cabalgatas', 4, 290.00, 'DISPONIBLE', 'Patio Logístico Exterior'),
    -- PERSONAL_TECNICO
    (8, 'PER-001', 'Técnico Superior de Sonido Directo (FOH / Monitores)', 'PERSONAL_TECNICO', 'Ingeniero de sonido titulado para ajuste de sistemas, mezcla en vivo y microfonía RF', 8, 220.00, 'DISPONIBLE', 'Plantilla Técnica Interna'),
    (9, 'PER-002', 'Operador de Iluminación DMX y Vídeo (GrandMA)', 'PERSONAL_TECNICO', 'Técnico especialista en programación de consolas de luces y servidores de vídeo', 6, 210.00, 'DISPONIBLE', 'Plantilla Técnica Interna'),
    (10, 'PER-003', 'Rigger / Montador de Estructuras y Escenarios', 'PERSONAL_TECNICO', 'Personal técnico certificado en trabajos en altura, montaje de truss y carga/descarga', 16, 150.00, 'DISPONIBLE', 'Plantilla Técnica Interna'),
    (24, 'PER-004', 'Regidor de Escenario y Coordinador de Producción en Vivo', 'PERSONAL_TECNICO', 'Responsable técnico de tiempos de gala, coordinación de artistas, ponentes y seguridad escénica', 4, 240.00, 'DISPONIBLE', 'Plantilla Técnica Interna'),
    -- ANIMADORES
    (11, 'ANI-001', 'Animadores Infantiles, Pasacalles y Personajes', 'ANIMADORES', 'Equipo de animación sociocultural con vestuario temático, juegos, globoflexia y bailes', 20, 130.00, 'DISPONIBLE', 'Bolsa de Animadores y Artistas'),
    (12, 'ANI-002', 'Speaker / Presentador y DJ Animador de Eventos', 'ANIMADORES', 'Locutor profesional y DJ animador para galas, entregas de premios y fiestas populares', 6, 250.00, 'DISPONIBLE', 'Bolsa de Animadores y Artistas'),
    (25, 'ANI-003', 'Comparsa de Zancudos, Malabaristas LED y Fuego', 'ANIMADORES', 'Artistas de circo y teatro de calle para cabalgatas, inauguraciones y desfiles nocturnos', 8, 185.00, 'DISPONIBLE', 'Bolsa de Animadores y Artistas'),
    -- OTROS
    (26, 'OTR-001', 'Kit de Pasacables de Caucho 5 Canales + Cuadro Eléctrico 63A', 'OTROS', 'Lote de 20 metros de pasacables de tráfico pesado y subcuadro eléctrico con protecciones diferenciales', 10, 60.00, 'DISPONIBLE', 'Nave Central - Estantería Eléctrica D1'),
    (27, 'OTR-002', 'Flota de 12 Walkie-Talkies Motorola DP1400 UHF + Pinganillos', 'OTROS', 'Maleta de intercomunicación profesional con cargador múltiple para equipo de producción y seguridad', 5, 70.00, 'DISPONIBLE', 'Nave Central - Cuarto de Producción')
ON DUPLICATE KEY UPDATE
    cantidad_total = GREATEST(cantidad_total, VALUES(cantidad_total));

-- ===================================================================
-- 6. ASIGNACIONES DE STOCK Y PERSONAL A EVENTOS
--    Cubre estados RESERVADO, EN_USO y DEVUELTO en los 12 eventos
-- ===================================================================
INSERT IGNORE INTO asignaciones_evento (id, evento_id, recurso_id, cantidad, fecha_asignacion, estado_asignacion, responsable, observaciones) VALUES
    -- Evento 1: Cabalgata de Reyes 2027 (CONFIRMADO)
    (1, 1, 1, 2, '2026-11-01', 'RESERVADO', 'Carlos Molina (Jefe Sonido)', 'Sonorización carrozas principales y Plaza del Carmen'),
    (2, 1, 11, 8, '2026-11-01', 'RESERVADO', 'Laura Gómez (Coord. Animación)', 'Pasacalles real y animación infantil durante todo el recorrido'),
    (7, 1, 25, 4, '2026-11-02', 'RESERVADO', 'Laura Gómez (Coord. Animación)', 'Zancudos y malabaristas LED abriendo el cortejo real'),
    (8, 1, 23, 2, '2026-11-02', 'RESERVADO', 'Miguel Ángel Ruiz', 'Vallado de seguridad en salida y llegada en Plaza del Carmen'),

    -- Evento 2: Fiestas Populares y Conciertos de Verano (EN_PROCESO)
    (3, 2, 7, 1, '2026-07-10', 'EN_USO', 'Miguel Ángel Ruiz', 'Montaje en Plaza de España 48h antes del inicio'),
    (4, 2, 3, 12, '2026-07-10', 'EN_USO', 'Elena Sánchez (Iluminación)', 'Puente trasero y frontal del escenario principal'),
    (5, 2, 8, 2, '2026-07-10', 'EN_USO', 'Carlos Molina (Jefe Sonido)', 'Un técnico en FOH y un técnico en monitores de escenario'),
    (6, 2, 12, 1, '2026-07-10', 'EN_USO', 'Laura Gómez (Coord. Animación)', 'DJ y speaker para cierre de conciertos nocturnos'),

    -- Evento 3: Campaña Navideña y Pista de Hielo (PRESUPUESTADO)
    (9, 3, 5, 2, '2026-11-03', 'RESERVADO', 'David Serrano (Vídeo)', 'Pantallas LED exteriores para cuenta atrás de encendido navideño'),
    (10, 3, 11, 6, '2026-11-03', 'RESERVADO', 'Laura Gómez (Coord. Animación)', 'Elfos, Papá Noel y dinamización en pista de hielo turnos de tarde'),
    (11, 3, 22, 4, '2026-11-03', 'RESERVADO', 'Miguel Ángel Ruiz', 'Carpas para taquilla de patines, vestuario y control técnico'),

    -- Evento 4: Torneo Nacional de Tenis Ciudad de la Alhambra (CONFIRMADO)
    (12, 4, 13, 1, '2026-09-01', 'RESERVADO', 'Carlos Molina (Jefe Sonido)', 'Microfonía inalámbrica para juez de silla y entrega de trofeos'),
    (13, 4, 12, 1, '2026-09-01', 'RESERVADO', 'Laura Gómez (Coord. Animación)', 'Speaker oficial para presentación de finalistas y ceremonia'),
    (14, 4, 22, 2, '2026-09-01', 'RESERVADO', 'Miguel Ángel Ruiz', 'Zona de sombra hospitality y acreditaciones de jugadores'),

    -- Evento 5: Festival Internacional de Música y Danza (FINALIZADO)
    (15, 5, 2, 2, '2026-06-18', 'DEVUELTO', 'Carlos Molina (Jefe Sonido)', 'Control FOH y grabación multipista orquesta sinfónica (revisado y devuelto)'),
    (16, 5, 16, 12, '2026-06-18', 'DEVUELTO', 'Elena Sánchez (Iluminación)', 'Iluminación escénica cálida en Jardines del Generalife'),
    (17, 5, 24, 2, '2026-06-18', 'DEVUELTO', 'Sofía Martín (Producción)', 'Regiduría de compañías internacionales de danza'),

    -- Evento 6: Congreso Andaluz de Innovación y Tecnología (EN_PROCESO)
    (18, 6, 19, 1, '2026-11-04', 'EN_USO', 'David Serrano (Vídeo)', 'Realización multicámara y streaming en directo ponencias plenarias'),
    (19, 6, 20, 2, '2026-11-04', 'EN_USO', 'David Serrano (Vídeo)', 'Doble proyección láser en pantalla panorámica del auditorio'),
    (20, 6, 13, 2, '2026-11-04', 'EN_USO', 'Carlos Molina (Jefe Sonido)', '16 canales de microfonía de diadema y atril para mesas redondas'),
    (21, 6, 27, 2, '2026-11-04', 'EN_USO', 'Sofía Martín (Producción)', 'Intercomunicación equipo de regiduría, vídeo y azafatas'),

    -- Evento 7: Gala de Bienvenida Universitaria UGR 2026 (FINALIZADO)
    (22, 7, 1, 2, '2026-10-06', 'DEVUELTO', 'Carlos Molina (Jefe Sonido)', 'Sistema PA principal en explanada Fuentenueva'),
    (23, 7, 10, 6, '2026-10-06', 'DEVUELTO', 'Miguel Ángel Ruiz', 'Montaje y desmontaje completo sin incidencias'),

    -- Evento 8: Festival Costa Tropical Sound Motril (CONFIRMADO)
    (24, 8, 1, 3, '2026-11-01', 'RESERVADO', 'Carlos Molina (Jefe Sonido)', 'Configuración festival doble hang + subgraves cardioides'),
    (25, 8, 6, 2, '2026-11-01', 'RESERVADO', 'Miguel Ángel Ruiz', 'Grupos electrógenos en paralelo para escenario y zona restauración'),
    (26, 8, 17, 8, '2026-11-01', 'RESERVADO', 'Elena Sánchez (Iluminación)', 'Cegadoras IP65 en frontal de escenario playa'),
    (27, 8, 10, 8, '2026-11-01', 'RESERVADO', 'Miguel Ángel Ruiz', 'Cuadrilla de riggers y montadores para turno de carga y volado'),

    -- Evento 9: Feria Gastronómica y Escenario Flamenco Málaga (EN_PROCESO)
    (28, 9, 7, 1, '2026-11-04', 'EN_USO', 'Miguel Ángel Ruiz', 'Escenario flamenco principal frente al mar en Muelle Uno'),
    (29, 9, 14, 6, '2026-11-04', 'EN_USO', 'Carlos Molina (Jefe Sonido)', 'Monitores de piso para cuadro flamenco (cante, toque y cajón)'),
    (30, 9, 9, 2, '2026-11-04', 'EN_USO', 'Elena Sánchez (Iluminación)', 'Operadores de luces para actuaciones de tarde y noche'),
    (31, 9, 26, 4, '2026-11-04', 'EN_USO', 'Miguel Ángel Ruiz', 'Protección de acometidas eléctricas en paseo peatonal'),

    -- Evento 10: Gira Indie Sur - Concierto Fin de Temporada (PRESUPUESTADO)
    (32, 10, 2, 2, '2026-11-05', 'RESERVADO', 'Carlos Molina (Jefe Sonido)', 'Consolas Yamaha CL5 para FOH y monitores según rider técnico'),
    (33, 10, 3, 10, '2026-11-05', 'RESERVADO', 'Elena Sánchez (Iluminación)', 'Contra-luz y efectos aéreos en auditorio'),

    -- Evento 11: Cena de Gala Anual y Entrega de Premios Empresariales (CONFIRMADO)
    (34, 11, 16, 8, '2026-11-05', 'RESERVADO', 'Elena Sánchez (Iluminación)', 'Recortes LED para atril de premiados y baño arquitectónico del salón'),
    (35, 11, 24, 1, '2026-11-05', 'RESERVADO', 'Sofía Martín (Producción)', 'Regiduría de escaleta de premios y discursos institucionales'),
    (36, 11, 12, 1, '2026-11-05', 'RESERVADO', 'Laura Gómez (Coord. Animación)', 'Presentador de gala y amenización musical post-cena'),

    -- Evento 12: Ciclo Cultural Noches en los Castillos de Jaén (FINALIZADO)
    (37, 12, 21, 2, '2026-07-22', 'DEVUELTO', 'Miguel Ángel Ruiz', 'Puentes truss para iluminación monumental (desmontado y en almacén)');
