# EventPass Tickets Service

Emite códigos UUID únicos por orden y permite que el staff valide cada ticket una sola vez. La emisión es idempotente por `orderId` para tolerar reintentos del procesador. Java 21, Spring Boot, Maven y PostgreSQL (`eventpass_tickets`).

## Ejecutar

Desde la raíz EventPass: `docker compose up --build`. Directo: configura `DB_URL`, `DB_USER`, `DB_PASSWORD`, `JWT_SECRET`; ejecuta `mvn clean test package` y `mvn spring-boot:run`.

Puerto predeterminado: `8084`; usar `http://localhost:8080` por el Gateway.

## Endpoints

- `GET /api/tickets/mine` requiere JWT `BUYER`.
- `POST /api/tickets/validate/{code}` requiere JWT `STAFF`: primer uso devuelve `200`; segundo uso `409`; código no registrado `404`.
- `POST /api/tickets/internal/issue` es interno para el procesador, no para llamadas de usuario.
