-- Script para configurar la base de datos para el microservicio de Órdenes

-- 1. Crear la base de datos 'orders_db'
CREATE DATABASE orders_db;

-- 2. Crear el usuario 'usuariodb_ordenes' con contraseña 'ordenes1324'
CREATE USER usuariodb_ordenes WITH PASSWORD 'ordenes1324';

-- 3. Otorgar todos los privilegios en la base de datos 'orders_db' al usuario 'usuariodb_ordenes'
GRANT ALL PRIVILEGES ON DATABASE orders_db TO usuariodb_ordenes;

-- 4. Conectarse a la base de datos 'orders_db' para otorgar permisos en el esquema y tablas
\connect orders_db

-- 5. Otorgar permisos de uso y creación en el esquema 'public'
GRANT USAGE ON SCHEMA public TO usuariodb_ordenes;
GRANT CREATE ON SCHEMA public TO usuariodb_ordenes;

-- 6. Otorgar permisos en tablas y secuencias existentes (si las hubiera)
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO usuariodb_ordenes;
GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO usuariodb_ordenes;

-- 7. Configurar privilegios predeterminados para futuras tablas y secuencias
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO usuariodb_ordenes;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO usuariodb_ordenes;
