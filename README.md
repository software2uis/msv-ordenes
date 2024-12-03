# **README: Configuración del Microservicio de Órdenes**

Este documento proporciona instrucciones para configurar el entorno de desarrollo del microservicio de Órdenes, incluyendo la instalación de PostgreSQL, creación de la base de datos y usuario, y configuración de la aplicación Spring Boot.

---

## **Tabla de Contenidos**

1. [Prerequisitos](#prerequisitos)
2. [Instalación de PostgreSQL](#instalación-de-postgresql)
3. [Creación de la Base de Datos y Usuario](#creación-de-la-base-de-datos-y-usuario)
4. [Configuración en la Aplicación Spring Boot](#configuración-en-la-aplicación-spring-boot)
5. [Ejecución de la Aplicación](#ejecución-de-la-aplicación)
6. [Resolución de Problemas](#resolución-de-problemas)

---

## **Prerequisitos**

- **Java JDK 17** o superior.
- **Maven** instalado o uso de Maven Wrapper.
- **Git** instalado.
- **IDE**: IntelliJ IDEA u otro compatible.
- **Node.js y NPM** instalados (para el frontend en Angular).
- **PostgreSQL** versión 12 o superior.

---

## **Instalación de PostgreSQL**

### **Windows**

1. **Descargar PostgreSQL** desde la página oficial: [PostgreSQL Downloads](https://www.postgresql.org/download/windows/).

2. **Instalar PostgreSQL** siguiendo las instrucciones del asistente.

   - **Importante**: Durante la instalación, establece una contraseña para el usuario `postgres`. Anota esta contraseña para futuros pasos.

3. **Verificar la Instalación**:

   - Abre el **Símbolo del sistema** y ejecuta:

     ```bash
     psql --version
     ```

   - Si el comando no es reconocido, utiliza la consola "SQL Shell (psql)" desde el menú Inicio.

### **MacOS**

1. **Instalar PostgreSQL** usando Homebrew:

   ```bash
   brew install postgresql
   brew services start postgresql
   ```

2. **Verificar la Instalación**:

   ```bash
   psql --version
   ```

### **Linux (Ubuntu/Debian)**

1. **Actualizar e Instalar PostgreSQL**:

   ```bash
   sudo apt update
   sudo apt install postgresql postgresql-contrib
   ```

2. **Verificar la Instalación**:

   ```bash
   psql --version
   ```

---

## **Creación de la Base de Datos y Usuario**

### **Opción 1: Ejecutar el Script SQL**

1. **Crear el Archivo del Script**

   - Crea un archivo llamado `setup_database.sql` con el siguiente contenido:

     ```sql
     -- Script para configurar la base de datos para el microservicio de Órdenes

     -- 1. Crear la base de datos 'orders_db'
     CREATE DATABASE orders_db;

     -- 2. Crear el usuario 'usuariodb_ordenes' con contraseña 'ordenes1324'
     CREATE USER usuariodb_ordenes WITH PASSWORD 'ordenes1324';

     -- 3. Otorgar todos los privilegios en la base de datos 'orders_db' al usuario 'usuariodb_ordenes'
     GRANT ALL PRIVILEGES ON DATABASE orders_db TO usuariodb_ordenes;

     -- 4. Conectarse a la base de datos 'orders_db'
     \connect orders_db

     -- 5. Otorgar permisos en el esquema 'public'
     GRANT USAGE ON SCHEMA public TO usuariodb_ordenes;
     GRANT CREATE ON SCHEMA public TO usuariodb_ordenes;

     -- 6. Otorgar permisos en tablas y secuencias
     GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO usuariodb_ordenes;
     GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO usuariodb_ordenes;

     -- 7. Configurar privilegios predeterminados para futuras tablas y secuencias
     ALTER DEFAULT PRIVILEGES IN SCHEMA public
     GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO usuariodb_ordenes;
     ALTER DEFAULT PRIVILEGES IN SCHEMA public
     GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO usuariodb_ordenes;
     ```

2. **Ejecutar el Script**

   - **Usando `psql` en Windows**:

     ```cmd
     "C:\Program Files\PostgreSQL\14\bin\psql.exe" -U postgres -f "ruta\al\archivo\setup_database.sql"
     ```

     - Reemplaza `"ruta\al\archivo\setup_database.sql"` con la ruta donde guardaste el archivo.
     - Ingresa la contraseña del usuario `postgres` cuando se te solicite.

   - **Usando pgAdmin**:

     - Abre pgAdmin y conéctate al servidor como `postgres`.
     - Haz clic derecho en **"Databases"** y selecciona **"Query Tool"**.
     - Carga el script y ejecútalo presionando **F5** o haciendo clic en el botón de ejecución.

### **Opción 2: Ejecutar Comandos Manualmente**

1. **Acceder a la Consola de PostgreSQL como el Usuario `postgres`**

   - **Windows**:

     - Abre **SQL Shell (psql)** desde el menú Inicio.
     - Ingresa los datos solicitados, dejando los valores por defecto si es aplicable.

   - **MacOS/Linux**:

     ```bash
     sudo -u postgres psql
     ```

2. **Crear la Base de Datos y el Usuario**

   ```sql
   CREATE DATABASE orders_db;
   CREATE USER usuariodb_ordenes WITH PASSWORD 'ordenes1324';
   GRANT ALL PRIVILEGES ON DATABASE orders_db TO usuariodb_ordenes;
   ```

3. **Conectarse a la Base de Datos `orders_db`**

   ```sql
   \connect orders_db
   ```

4. **Otorgar Permisos en el Esquema y Tablas**

   ```sql
   GRANT USAGE ON SCHEMA public TO usuariodb_ordenes;
   GRANT CREATE ON SCHEMA public TO usuariodb_ordenes;
   GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO usuariodb_ordenes;
   GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA public TO usuariodb_ordenes;
   ALTER DEFAULT PRIVILEGES IN SCHEMA public
   GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO usuariodb_ordenes;
   ALTER DEFAULT PRIVILEGES IN SCHEMA public
   GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO usuariodb_ordenes;
   ```

5. **Salir de la Consola**

   ```sql
   \q
   ```

---

## **Configuración en la Aplicación Spring Boot**

Edita el archivo `application.properties` ubicado en `src/main/resources/` y agrega o actualiza las siguientes propiedades:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/orders_db
spring.datasource.username=usuariodb_ordenes
spring.datasource.password=ordenes1324

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

- **Explicación de las propiedades**:

  - `spring.datasource.url`: URL de conexión a la base de datos.
  - `spring.datasource.username`: Nombre de usuario de la base de datos.
  - `spring.datasource.password`: Contraseña del usuario de la base de datos.
  - `spring.jpa.hibernate.ddl-auto=update`: Actualiza el esquema de la base de datos según las entidades.
  - `spring.jpa.show-sql=true`: Muestra las consultas SQL en la consola.
  - `spring.jpa.properties.hibernate.format_sql=true`: Formatea las consultas SQL.
  - `spring.jpa.database-platform`: Especifica el dialecto de PostgreSQL para Hibernate.

---

## **Ejecución de la Aplicación**

### **Usando Maven Wrapper (Recomendado)**

1. **Navegar al Directorio del Proyecto**

   ```cmd
   cd ruta\al\directorio\msv-ordenes
   ```

2. **Ejecutar la Aplicación**

   - **Windows**:

     ```cmd
     .\mvnw.cmd spring-boot:run
     ```

   - **MacOS/Linux**:

     ```bash
     ./mvnw spring-boot:run
     ```

### **Usando Maven Instalado en el Sistema**

1. **Verificar la Instalación de Maven**

   ```cmd
   mvn -version
   ```

2. **Ejecutar la Aplicación**

   ```cmd
   mvn spring-boot:run
   ```

