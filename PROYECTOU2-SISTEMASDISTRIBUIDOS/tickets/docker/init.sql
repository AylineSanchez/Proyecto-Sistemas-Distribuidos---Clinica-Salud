-- ============================================
-- SISTEMA DE TICKETS - CLÍNICA SALUD
-- Base de datos para el sistema de atención
-- ============================================

-- Crear tabla de tickets
CREATE TABLE IF NOT EXISTS tickets (
    id SERIAL PRIMARY KEY,
    numero_ticket VARCHAR(10) NOT NULL UNIQUE,
    tipo_atencion VARCHAR(50) NOT NULL,
    nombre_paciente VARCHAR(100),
    rut VARCHAR(20),
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_llamado TIMESTAMP,
    fecha_atencion TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'pendiente',
    modulo_asignado VARCHAR(50),
    modulo_destino VARCHAR(50)
);

-- Índices para mejorar el rendimiento de las consultas
CREATE INDEX IF NOT EXISTS idx_tickets_estado ON tickets(estado);
CREATE INDEX IF NOT EXISTS idx_tickets_fecha_creacion ON tickets(fecha_creacion);
CREATE INDEX IF NOT EXISTS idx_tickets_modulo_destino ON tickets(modulo_destino);
CREATE INDEX IF NOT EXISTS idx_tickets_tipo_atencion ON tickets(tipo_atencion);

-- ============================================
-- DATOS DE PRUEBA - TODOS EN ESTADO PENDIENTE
-- ============================================

-- MÉDICO 1 - Consulta Médica (8 pacientes)
INSERT INTO tickets (numero_ticket, tipo_atencion, nombre_paciente, rut, estado, modulo_destino) VALUES
('A001', 'Consulta Médica', 'Juan Pérez', '12.345.678-9', 'pendiente', 'Médico 1'),
('A004', 'Consulta Médica', 'Roberto Contreras', '13.456.789-0', 'pendiente', 'Médico 1'),
('A007', 'Consulta Médica', 'Verónica Soto', '14.567.890-1', 'pendiente', 'Médico 1'),
('A010', 'Consulta Médica', 'Francisco Rojas', '15.678.901-2', 'pendiente', 'Médico 1'),
('A013', 'Consulta Médica', 'Daniela Toro', '16.789.012-3', 'pendiente', 'Médico 1'),
('A016', 'Consulta Médica', 'Cristóbal Núñez', '17.890.123-4', 'pendiente', 'Médico 1'),
('A019', 'Consulta Médica', 'Francisca Alarcón', '18.901.234-5', 'pendiente', 'Médico 1'),
('A022', 'Consulta Médica', 'Marcelo Díaz', '19.012.345-6', 'pendiente', 'Médico 1');

-- MÉDICO 2 - Consulta Médica (8 pacientes)
INSERT INTO tickets (numero_ticket, tipo_atencion, nombre_paciente, rut, estado, modulo_destino) VALUES
('A002', 'Consulta Médica', 'María González', '23.456.789-1', 'pendiente', 'Médico 2'),
('A005', 'Consulta Médica', 'Javiera López', '24.567.890-2', 'pendiente', 'Médico 2'),
('A008', 'Consulta Médica', 'Pablo Herrera', '25.678.901-3', 'pendiente', 'Médico 2'),
('A011', 'Consulta Médica', 'Isidora Reyes', '26.789.012-4', 'pendiente', 'Médico 2'),
('A014', 'Consulta Médica', 'Matías Zamora', '27.890.123-5', 'pendiente', 'Médico 2'),
('A017', 'Consulta Médica', 'Valentina Pizarro', '28.901.234-6', 'pendiente', 'Médico 2'),
('A020', 'Consulta Médica', 'Benjamín Salinas', '29.012.345-7', 'pendiente', 'Médico 2'),
('A023', 'Consulta Médica', 'Fernando Ávila', '20.123.456-7', 'pendiente', 'Médico 2');

-- MÉDICO 3 - Consulta Médica (8 pacientes)
INSERT INTO tickets (numero_ticket, tipo_atencion, nombre_paciente, rut, estado, modulo_destino) VALUES
('A003', 'Consulta Médica', 'Ana Martínez', '34.567.890-2', 'pendiente', 'Médico 3'),
('A006', 'Consulta Médica', 'Catalina Fuentes', '35.678.901-3', 'pendiente', 'Médico 3'),
('A009', 'Consulta Médica', 'Tomás Vergara', '36.789.012-4', 'pendiente', 'Médico 3'),
('A012', 'Consulta Médica', 'Antonia Castro', '37.890.123-5', 'pendiente', 'Médico 3'),
('A015', 'Consulta Médica', 'Nicolás Valdés', '38.901.234-6', 'pendiente', 'Médico 3'),
('A018', 'Consulta Médica', 'Consuelo Cáceres', '39.012.345-7', 'pendiente', 'Médico 3'),
('A021', 'Consulta Médica', 'Emilio Godoy', '40.123.456-8', 'pendiente', 'Médico 3'),
('A024', 'Consulta Médica', 'Patricia Ulloa', '41.234.567-9', 'pendiente', 'Médico 3');

-- LABORATORIO A - Toma de Muestras (6 pacientes)
INSERT INTO tickets (numero_ticket, tipo_atencion, nombre_paciente, rut, estado, modulo_destino) VALUES
('B001', 'Toma de Muestras', 'Carlos López', '45.678.901-3', 'pendiente', 'Laboratorio A'),
('B003', 'Toma de Muestras', 'Camila Espinoza', '46.789.012-4', 'pendiente', 'Laboratorio A'),
('B005', 'Toma de Muestras', 'Lucas Guzmán', '47.890.123-5', 'pendiente', 'Laboratorio A'),
('B007', 'Toma de Muestras', 'Josefa Valenzuela', '48.901.234-6', 'pendiente', 'Laboratorio A'),
('B009', 'Toma de Muestras', 'Martín Riquelme', '49.012.345-7', 'pendiente', 'Laboratorio A'),
('B011', 'Toma de Muestras', 'Silvia Lira', '50.123.456-8', 'pendiente', 'Laboratorio A');

-- LABORATORIO B - Toma de Muestras (6 pacientes)
INSERT INTO tickets (numero_ticket, tipo_atencion, nombre_paciente, rut, estado, modulo_destino) VALUES
('B002', 'Toma de Muestras', 'Pedro Silva', '54.789.012-4', 'pendiente', 'Laboratorio B'),
('B004', 'Toma de Muestras', 'Fernanda Orellana', '55.890.123-5', 'pendiente', 'Laboratorio B'),
('B006', 'Toma de Muestras', 'Cristian Flores', '56.901.234-6', 'pendiente', 'Laboratorio B'),
('B008', 'Toma de Muestras', 'Monserrat Leiva', '57.012.345-7', 'pendiente', 'Laboratorio B'),
('B010', 'Toma de Muestras', 'Joaquín Ponce', '58.123.456-8', 'pendiente', 'Laboratorio B'),
('B012', 'Toma de Muestras', 'Rosa Marín', '59.234.567-9', 'pendiente', 'Laboratorio B');

-- VENTANILLA 1 - Retiro de Resultados (5 pacientes)
INSERT INTO tickets (numero_ticket, tipo_atencion, nombre_paciente, rut, estado, modulo_destino) VALUES
('C001', 'Retiro de Resultados', 'Laura Fernández', '63.890.123-5', 'pendiente', 'Ventanilla 1'),
('C003', 'Retiro de Resultados', 'Rodrigo Sepúlveda', '64.901.234-6', 'pendiente', 'Ventanilla 1'),
('C005', 'Retiro de Resultados', 'Paula González', '65.012.345-7', 'pendiente', 'Ventanilla 1'),
('C007', 'Retiro de Resultados', 'Hugo Castillo', '66.123.456-8', 'pendiente', 'Ventanilla 1'),
('C009', 'Retiro de Resultados', 'Eduardo Labra', '67.234.567-9', 'pendiente', 'Ventanilla 1');

-- VENTANILLA 2 - Retiro de Resultados (5 pacientes)
INSERT INTO tickets (numero_ticket, tipo_atencion, nombre_paciente, rut, estado, modulo_destino) VALUES
('C002', 'Retiro de Resultados', 'Andrea Tapia', '73.901.234-6', 'pendiente', 'Ventanilla 2'),
('C004', 'Retiro de Resultados', 'Mario Parra', '74.012.345-7', 'pendiente', 'Ventanilla 2'),
('C006', 'Retiro de Resultados', 'Daniel Moya', '75.123.456-8', 'pendiente', 'Ventanilla 2'),
('C008', 'Retiro de Resultados', 'Susana Cerda', '76.234.567-9', 'pendiente', 'Ventanilla 2'),
('C010', 'Retiro de Resultados', 'Ricardo Torrealba', '77.345.678-0', 'pendiente', 'Ventanilla 2');

-- ============================================
-- TABLA DE AUDITORÍA (requisito retención 5+ años)
-- ============================================
CREATE TABLE IF NOT EXISTS tickets_auditoria (
    id SERIAL PRIMARY KEY,
    ticket_id INTEGER,
    accion VARCHAR(50),
    usuario VARCHAR(50),
    detalles TEXT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- CONSULTA DE VERIFICACIÓN
-- ============================================
SELECT 
    modulo_destino,
    COUNT(*) as cantidad
FROM tickets 
GROUP BY modulo_destino 
ORDER BY modulo_destino;