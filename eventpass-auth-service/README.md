# EventPass Auth Service

Servicio de autenticación y roles `BUYER`/`STAFF`. Java 21, Spring Boot, Maven y PostgreSQL (`eventpass_auth`). Emite JWT HS256 de demostración con expiración de dos horas y guarda contraseñas con PBKDF2.

## Ejecutar en Compose

Desde la raíz del proyecto EventPass: `docker compose up --build`. El acceso externo debe ser a través del Gateway `http://localhost:8080`.

## Ejecutar directamente

Requiere PostgreSQL con base `eventpass_auth`. Configura `DB_URL`, `DB_USER`, `DB_PASSWORD` y `JWT_SECRET`; luego:

```bash
mvn clean test package
mvn spring-boot:run
```

Puerto predeterminado: `8081`.

## Endpoints

- `POST /api/auth/register` body `{ "username": "comprador1", "password": "ClaveDemo123", "role": "BUYER" }`
- `POST /api/auth/login` body `{ "username": "comprador1", "password": "ClaveDemo123" }`

Para la demostración el registro acepta `STAFF`; en producción la asignación de ese rol debe restringirse a un administrador. No uses el secreto de demostración fuera de local.
