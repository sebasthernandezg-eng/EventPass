# EventPass Events Service

Microservicio para crear, consultar, actualizar y eliminar eventos y administrar el aforo. Java 21, Spring Boot, Maven y PostgreSQL (`eventpass_events`). La reserva de cupos usa una actualización atómica y registra el `orderId` para que un reintento de SQS no descuente el aforo dos veces.

## Ejecutar

En Compose se inicia con `docker compose up --build` desde la carpeta EventPass. Directo: configura `DB_URL`, `DB_USER`, `DB_PASSWORD`, `JWT_SECRET` y ejecuta `mvn clean test package` seguido de `mvn spring-boot:run`.

Puerto predeterminado: `8082`; en la demostración externa usa el Gateway `http://localhost:8080`.

## Endpoints

- `GET /api/events` y `GET /api/events/{id}` requieren JWT.
- `POST /api/events`, `PUT /api/events/{id}`, `DELETE /api/events/{id}` requieren rol `STAFF`.
- `POST /api/events/internal/{id}/reserve?quantity=1&orderId=1` es interno para el procesador.

Atributos de evento: `name`, `location`, `eventDate` (`YYYY-MM-DDTHH:mm:ss`), `capacity` y `available`. No hay FK hacia órdenes porque cada dominio tiene su propia base.
