# ForoHub API

API REST desarrollada con **Spring Boot 3** como parte del challenge **ForoHub**. Esta API permite gestionar tópicos, usuarios y autenticación con JWT.

## Tabla de Contenidos

- [Características](#características)
- [Requisitos](#requisitos)
- [Configuración](#configuración)
- [Migraciones con Flyway](#migraciones-con-flyway)
- [Dependencias](#dependencias)
- [Ejecución](#ejecución)
- [Contribuciones](#contribuciones)
- [Licencia](#licencia)

---

## Características

- Gestión de tópicos:
  - Creación, actualización, listado y eliminación lógica.
- Gestión de usuarios:
  - Creación de usuarios y autenticación con JWT.
- Integración con una base de datos MySQL.
- Uso de Flyway para control de migraciones.
- Seguridad mediante Spring Security y JSON Web Tokens (JWT).

## Requisitos

Asegúrate de tener instalado lo siguiente:

- **Java 17**
- **Maven 3.8+**
- **MySQL Server**
- Herramienta de cliente para MySQL (opcional): MySQL Workbench, DBeaver, etc.

## Configuración

1. Clona este repositorio en tu máquina local:
   ```bash
   git clone https://github.com/tu-usuario/foro-hub-api.git
   cd foro-hub-api
2. Configura la conexión a la base de datos en el archivo application.properties:
   ```bash
   spring.datasource.url=jdbc:mysql://localhost/foro_api
   spring.datasource.username=tu_usuario_mysql
   spring.datasource.password=tu_contraseña_mysql
3. Configura el secreto para JWT en el archivo application.properties:
    ```bash
    api.security.secret=tu_secreto_jwt
4. Crea la base de datos en MySQL:
   ```bash
   CREATE DATABASE foro_api;

## Migraciones con Flyway

Este proyecto utiliza Flyway para manejar las migraciones de la base de datos. Asegúrate de que la base de datos esté creada y luego ejecuta la aplicación para aplicar las migraciones:

- Primera migración: creación de la tabla topicos.
- Segunda migración: alteración de la tabla topicos para agregar el campo activo.
- Tercera migración: creación de la tabla usuarios.
  
Los scripts de migración se encuentran en la carpeta src/main/resources/db/migration.

## Dependencias

Las principales dependencias utilizadas en el proyecto son:

- Spring Boot Starter Web: Desarrollo de APIs REST.
- Spring Boot Starter Data JPA: Persistencia de datos.
- MySQL Connector: Conexión con la base de datos MySQL.
- Spring Boot Starter Security: Seguridad y autenticación.
- Java JWT: Generación y validación de tokens JWT.
- Flyway Core: Migración y control de versiones de la base de datos.
  
Para ver todas las dependencias, revisa el archivo pom.xml.

## Ejecución

1. Compila y empaqueta el proyecto con Maven:
   ```bash
   mvn clean package
2. Ejecuta la aplicación
   ```bash
   mvn spring-boot:run
3. Accede a la API en:
   ```bash
   http://localhost:8080
## Licencia

Este proyecto está licenciado bajo la MIT License.




