# Backend Users App

Una API REST desarrollada con Spring Boot para la gestión de usuarios, que incluye operaciones CRUD completas y integración con base de datos MySQL.

## 📋 Tabla de Contenidos

- [Características](#-características)
- [Tecnologías](#-tecnologías)
- [Arquitectura](#-arquitectura)
- [Prerrequisitos](#-prerrequisitos)
- [Instalación](#-instalación)
- [Configuración](#-configuración)
- [Uso](#-uso)
- [API Endpoints](#-api-endpoints)
- [Base de Datos](#-base-de-datos)
- [Testing](#-testing)
- [Docker](#-docker)
- [Estructura del Proyecto](#-estructura-del-proyecto)

## 🚀 Características

- ✅ API REST completa para gestión de usuarios
- ✅ Operaciones CRUD (Create, Read, Update, Delete)
- ✅ Integración con base de datos MySQL
- ✅ Validación de datos de entrada
- ✅ Manejo de errores HTTP
- ✅ Configuración con Docker Compose
- ✅ Logging de SQL para desarrollo
- ✅ Actuator para monitoreo

## 🛠 Tecnologías

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Data JPA**
- **Spring Web**
- **MySQL 8.0**
- **Maven 3.x**
- **Docker & Docker Compose**

### Dependencias principales

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

## 🏗 Arquitectura

El proyecto sigue la arquitectura de capas de Spring Boot:

``` bash
Controller Layer    → UserController
Service Layer       → UserService + UserServiceImpl
Repository Layer    → UserRepository
Entity Layer        → User
```

## 📋 Prerrequisitos

- Java 21+
- Maven 3.6+
- Docker y Docker Compose
- MySQL 8.0 (o usar el contenedor Docker proporcionado)

## 🔧 Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/jjgarcia6/spring-userapp.git
cd spring-userapp/backend-usersapp
```

### 2. Levantar la base de datos

```bash
docker-compose up -d mysql
```

### 3. Verificar que MySQL esté ejecutándose

```bash
docker ps
```

### 4. Crear la base de datos y usuario (si es necesario)

```bash
docker exec -it mysql-usersapp mysql -u root -p
```

```sql
CREATE DATABASE IF NOT EXISTS db_users_springboot;
CREATE USER IF NOT EXISTS 'usersapp_user'@'%' IDENTIFIED BY 'sasa1234';
GRANT ALL PRIVILEGES ON db_users_springboot.* TO 'usersapp_user'@'%';
FLUSH PRIVILEGES;
EXIT;
```

### 5. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en: `http://localhost:8080`

## ⚙️ Configuración

### application.properties

```properties
spring.application.name=backend-usersapp
spring.datasource.url=jdbc:mysql://localhost:3306/db_users_springboot
spring.datasource.username=usersapp_user
spring.datasource.password=sasa1234
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
logging.level.org.hibernate.SQL=DEBUG
```

### Variables de entorno

Puedes sobrescribir la configuración usando variables de entorno:

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/db_users_springboot
export SPRING_DATASOURCE_USERNAME=usersapp_user
export SPRING_DATASOURCE_PASSWORD=sasa1234
```

## 📖 Uso

### Verificar que la aplicación está funcionando

```bash
curl http://localhost:8080/actuator/health
```

Respuesta esperada:
```json
{"status":"UP"}
```

## 🔗 API Endpoints

| Método | Endpoint | Descripción | Request Body |
|--------|----------|-------------|--------------|
| GET | `/users` | Obtener todos los usuarios | - |
| GET | `/users/{id}` | Obtener usuario por ID | - |
| POST | `/users` | Crear nuevo usuario | JSON User |
| PUT | `/users/{id}` | Actualizar usuario | JSON User |
| DELETE | `/users/{id}` | Eliminar usuario | - |

### Ejemplos de uso con curl:

#### 1. Obtener todos los usuarios
```bash
curl -X GET http://localhost:8080/users
```

#### 2. Obtener usuario por ID
```bash
curl -X GET http://localhost:8080/users/1
```

#### 3. Crear nuevo usuario
```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "password123"
  }'
```

#### 4. Actualizar usuario
```bash
curl -X PUT http://localhost:8080/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_updated",
    "email": "john.updated@example.com",
    "password": "newpassword123"
  }'
```

#### 5. Eliminar usuario
```bash
curl -X DELETE http://localhost:8080/users/1
```

### Estructura del JSON de Usuario

```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "password": "password123"
}
```

### Códigos de respuesta HTTP

- `200 OK` - Operación exitosa
- `201 Created` - Usuario creado exitosamente
- `404 Not Found` - Usuario no encontrado
- `400 Bad Request` - Datos de entrada inválidos
- `500 Internal Server Error` - Error del servidor

## 🗄️ Base de Datos

### Esquema de la tabla `users`

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR(20) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);
```

### Restricciones
- `username`: único, máximo 30 caracteres
- `email`: único, máximo 100 caracteres
- `password`: máximo 20 caracteres
- `id`: clave primaria auto-incrementable

## 🧪 Testing

### Ejecutar tests unitarios

```bash
./mvnw test
```

### Ejecutar tests de integración

```bash
./mvnw verify
```

### Testing con Postman

1. Importar la colección de Postman (si está disponible)
2. Configurar el entorno base URL: `http://localhost:8080`
3. Ejecutar las pruebas de los endpoints

## 🐳 Docker

### docker-compose.yml

```yaml
services:
  mysql:
    image: mysql:8.0
    container_name: mysql-usersapp
    environment:
      MYSQL_ROOT_PASSWORD: sasa1234
      MYSQL_DATABASE: db_users_springboot
      MYSQL_USER: usersapp_user
      MYSQL_PASSWORD: sasa1234
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql
    restart: unless-stopped

volumes:
  mysql_data:
```

### Comandos útiles de Docker:

```bash
# Levantar solo MySQL
docker-compose up -d mysql

# Ver logs de MySQL
docker-compose logs -f mysql

# Parar todos los servicios
docker-compose down

# Parar y eliminar volúmenes
docker-compose down -v

# Conectar a MySQL desde el contenedor
docker exec -it mysql-usersapp mysql -u usersapp_user -p
```

## 📁 Estructura del Proyecto

```
backend-usersapp/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/backend/usersapp/backend_usersapp/
│   │   │       ├── BackendUsersappApplication.java
│   │   │       ├── controllers/
│   │   │       │   └── UserController.java
│   │   │       ├── models/
│   │   │       │   └── entities/
│   │   │       │       └── User.java
│   │   │       ├── repositories/
│   │   │       │   └── UserRepository.java
│   │   │       └── services/
│   │   │           ├── UserService.java
│   │   │           └── UserServiceImpl.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
│           └── com/backend/usersapp/backend_usersapp/
│               └── BackendUsersappApplicationTests.java
├── target/
├── docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
├── HELP.md
└── README.md
```

### Descripción de componentes

- **BackendUsersappApplication.java**: Clase principal de Spring Boot
- **UserController.java**: Controlador REST que maneja las peticiones HTTP
- **User.java**: Entidad JPA que representa la tabla de usuarios
- **UserRepository.java**: Interfaz de repositorio para operaciones de base de datos
- **UserService.java**: Interfaz de servicio para lógica de negocio
- **UserServiceImpl.java**: Implementación del servicio de usuarios
- **application.properties**: Configuración de la aplicación
- **docker-compose.yml**: Configuración de Docker para MySQL

## 🚨 Solución de Problemas

### Error de conexión a MySQL

Si obtienes el error: `Access denied for user 'usersapp_user'@'172.18.0.1'`

1. Verifica que MySQL esté ejecutándose:
   ```bash
   docker ps
   ```

2. Conecta como root y verifica el usuario:
   ```bash
   docker exec -it mysql-usersapp mysql -u root -p
   ```

3. Recrea el usuario:
   ```sql
   DROP USER IF EXISTS 'usersapp_user'@'%';
   CREATE USER 'usersapp_user'@'%' IDENTIFIED BY 'sasa1234';
   GRANT ALL PRIVILEGES ON db_users_springboot.* TO 'usersapp_user'@'%';
   FLUSH PRIVILEGES;
   ```

### Puerto 8080 en uso

Si el puerto 8080 está ocupado, cambiar en `application.properties`:
```properties
server.port=8081
```

### Error 404 en endpoints

Verifica que el controlador esté correctamente anotado con `@RestController` y `@RequestMapping("/users")`.

## 📝 Notas de Desarrollo

- La aplicación usa `spring.jpa.hibernate.ddl-auto=update` para crear/actualizar tablas automáticamente
- Se incluye logging SQL para desarrollo (`spring.jpa.show-sql=true`)
- El proyecto usa Java 21 como versión mínima
- Se recomienda usar Postman o similar para testing de la API

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request
