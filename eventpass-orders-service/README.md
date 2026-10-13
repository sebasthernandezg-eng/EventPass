# EventPass Orders Service

Registra una compra como `ACEPTADA_PENDIENTE` y publica el mensaje en SQS; no emite tickets sincrónicamente. Java 21, Spring Boot, Maven, PostgreSQL (`eventpass_orders`) y AWS SDK SQS. LocalStack emula SQS para la ejecución local.

## Ejecutar

Desde la raíz EventPass: `docker compose up --build`. Directo, configura `DB_URL`, `DB_USER`, `DB_PASSWORD`, `JWT_SECRET`, `SQS_ENDPOINT`, `SQS_QUEUE_URL` y credenciales de prueba LocalStack (`AWS_ACCESS_KEY_ID=test`, `AWS_SECRET_ACCESS_KEY=test`). Luego ejecuta `mvn clean test package` y `mvn spring-boot:run`.

Puerto predeterminado: `8083`; llamada de usuario por `http://localhost:8080`.

## Endpoints

- `POST /api/orders` con JWT `BUYER`, body `{ "eventId": 1, "quantity": 1 }`; responde `202 Accepted`.
- `GET /api/orders` lista las órdenes del comprador autenticado.
- `GET /api/orders/{id}` consulta una orden propia.
- `PATCH /api/orders/internal/{id}/status?value=EMITIDA` es interno.

El identificador de comprador viene del JWT; no se acepta desde el body para evitar suplantación.
