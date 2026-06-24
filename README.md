# Order Management

API REST para la gestión de clientes y consulta de órdenes de Liverpool, construida con Spring Boot siguiendo una arquitectura hexagonal (puertos y adaptadores).

## Stack Tecnológico

- **Java 26** con **Spring Boot 4.1.0**
- **MongoDB** (persistencia de clientes)
- **Maven** (build)
- **Docker** y **Docker Compose** (despliegue)
- **SpringDoc OpenAPI 3** (documentación Swagger)

## Arquitectura

El proyecto sigue una arquitectura hexagonal (ports & adapters):

```
com.liverpool.orders
├── domain/          → Entidades, value objects, interfaces de puerto
├── application/     → Casos de uso (implementaciones)
└── infrastructure/  → Adaptadores (REST controller, MongoDB, clientes externos)
```

## Requisitos

- [Docker](https://docs.docker.com/get-docker/) (recomendado)
- [Docker Compose](https://docs.docker.com/compose/install/) (incluido con Docker Desktop)

## Inicio rápido

### Levantar los servicios

```bash
docker compose up --build
```

Esto construye la imagen del Spring Boot y levanta junto con MongoDB.

### Detener los servicios

```bash
docker compose down
```

Los datos de MongoDB se conservan en el volumen `mongo_data`.

### Detener y eliminar volúmenes

```bash
docker compose down -v
```

## Servicios

| Servicio     | Puerto | Descripción                        |
|--------------|--------|------------------------------------|
| `mongodb`    | 27017  | Base de datos MongoDB              |
| `spring-boot`| 8080   | API REST de gestión de órdenes     |

## Variables de entorno

| Variable                  | Valor por defecto                                                      | Descripción                     |
|---------------------------|------------------------------------------------------------------------|---------------------------------|
| `SPRING_DATA_MONGODB_URI` | `mongodb://localhost:27017/order_management`                           | URI de conexión a MongoDB       |
| `MONGODB_DATABASE`        | `order_management`                                                     | Nombre de la base de datos      |
| `SERVER_PORT`             | `8080`                                                                 | Puerto del servidor             |
| `PEDIDOS_URL`             | `https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1/pedidos`          | URL del servicio externo de pedidos |
| `ITEMS_URL`               | `https://6994a4eab081bc23e9c0f61e.mockapi.io/api/v1/items`            | URL del servicio externo de items |

## API Endpoints

| Método | Ruta                        | Descripción                     |
|--------|-----------------------------|---------------------------------|
| POST   | `/api/v1/customers`         | Crear un nuevo cliente          |
| GET    | `/api/v1/customers/{userId}`| Obtener cliente + órdenes       |
| PUT    | `/api/v1/customers/{userId}`| Actualizar datos del cliente    |

## Documentación Swagger

Una vez levantado el servicio, accede a:

```
http://localhost:8080/swagger-ui/index.html
```

## Desarrollo sin Docker

```bash
# Compilar
./mvnw clean package -DskipTests

# Ejecutar
java -jar target/order-management-1.0-SNAPSHOT.jar
```

> **Nota:** Si no tienes el Maven wrapper (`mvnw`), instala Maven y ejecuta `mvn` directamente.

## Notas

- El Dockerfile usa un build multi-etapa para mantener la imagen final ligera.
- MongoDB incluye un healthcheck que espera a que el servicio esté listo antes de iniciar Spring Boot.
- El `.dockerignore` excluye `target/`, `.git/` y `.idea/` del contexto de build para acelerar las compilaciones.
