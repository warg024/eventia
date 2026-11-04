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
