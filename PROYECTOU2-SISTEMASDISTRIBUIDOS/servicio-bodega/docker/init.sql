-- Crear tabla si no existe
CREATE TABLE IF NOT EXISTS insumos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    categoria VARCHAR(255) NOT NULL,
    cantidad INTEGER NOT NULL,
    lote VARCHAR(255),
    fecha_vencimiento DATE,
    unidad VARCHAR(255),
    proveedor VARCHAR(255)
);

-- Datos iniciales
INSERT INTO insumos (nombre, categoria, cantidad, lote, fecha_vencimiento, unidad, proveedor) VALUES
('Paracetamol 500mg', 'Medicamentos', 150, 'LOT-2024-001', '2026-12-31', 'comprimidos', 'Laboratorio Chile'),
('Ibuprofeno 400mg', 'Medicamentos', 8, 'LOT-2024-002', '2026-06-30', 'comprimidos', 'Laboratorio Chile'),
('Guantes de látex M', 'Material quirúrgico', 500, 'LOT-2024-010', '2027-01-01', 'pares', 'MedSupply'),
('Jeringas 5ml', 'Material quirúrgico', 200, 'LOT-2024-011', '2027-03-15', 'unidades', 'MedSupply'),
('Alcohol 70%', 'Higiene y limpieza', 50, 'LOT-2024-020', '2026-09-01', 'litros', 'CleanMed'),
('Gasas estériles', 'Material quirúrgico', 5, 'LOT-2024-012', '2027-06-01', 'paquetes', 'MedSupply'),
('Suero fisiológico 500ml', 'Medicamentos', 80, 'LOT-2024-003', '2025-11-30', 'frascos', 'Fresenius'),
('Termómetro digital', 'Equipos médicos', 12, 'LOT-2024-030', NULL, 'unidades', 'TechMed'),
('Mascarillas N95', 'Higiene y limpieza', 3, 'LOT-2024-021', '2026-12-01', 'unidades', 'ProtectMed'),
('Tiras reactivas glucosa', 'Insumos de laboratorio', 200, 'LOT-2024-040', '2026-08-01', 'tiras', 'LabPro')
ON CONFLICT DO NOTHING;
