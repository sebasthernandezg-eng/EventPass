# EventPass local — implementación base de EP2

Plataforma de venta de entradas para conciertos y eventos universitarios. Este paquete implementa los cuatro dominios del caso en servicios Spring Boot independientes y los expone por un Gateway local.

## Componentes

- `eventpass-auth-service`: registro de usuarios, roles `BUYER`/`STAFF` y emisión de token JWT local.
- `eventpass-events-service`: CRUD de eventos, reserva atómica de aforo e idempotencia por orden.
- `eventpass-orders-service`: guarda órdenes como `ACEPTADA_PENDIENTE` y publica mensajes SQS.
- `eventpass-tickets-service`: emite códigos únicos y valida cada ticket una sola vez.
- `eventpass-order-processor`: consumidor Java local que cumple la función de demostración del disparador Lambda.
- `eventpass-gateway`: entrada local en `http://localhost:8080`.
- PostgreSQL: una base por dominio. LocalStack: cola SQS local.

> Implementación de referencia con supuestos: el documento no incluye ERD ni atributos detallados. API Gateway se emula con Spring Cloud Gateway y Lambda con un proceso Java local; el flujo SQS usa LocalStack. No requiere ni despliega recursos AWS.

## Ejecutar

Requiere Docker Desktop con Compose v2 y Git. Desde esta carpeta:

```powershell
docker compose up --build
```

Para parar: `docker compose down`. Para borrar también las bases locales: `docker compose down -v` (elimina los datos).

## Prueba rápida en Postman

Base URL: `http://localhost:8080`. Registra un comprador y un staff:

```json
{"username":"comprador1","password":"ClaveDemo123","role":"BUYER"}
```

```json
{"username":"staff1","password":"ClaveDemo123","role":"STAFF"}
```

Inicia sesión con `POST /api/auth/login` y usa el token recibido como Bearer. Con token STAFF crea evento en `POST /api/events`:

```json
{"name":"Concierto de prueba","location":"Auditorio Central","eventDate":"2026-12-10T20:00:00","capacity":1}
```

Con token BUYER consulta `GET /api/events`, luego crea una orden en `POST /api/orders`:

```json
{"eventId":1,"quantity":1}
```

Debe responder `202 Accepted` con `ACEPTADA_PENDIENTE`. El consumidor procesa la cola y, unos segundos después, `GET /api/orders` debe mostrar `EMITIDA` o `RECHAZADA`. Consulta `GET /api/tickets/mine`; después usa token STAFF en `POST /api/tickets/validate/{accessCode}`. El primer uso responde `200`; el segundo, `409`.

Para demostrar que no se excede el aforo, crea un evento con capacidad 1 y envía dos órdenes. Una debería emitirse y la otra quedar rechazada.

## Endpoints principales

| Método | Ruta | Rol | Función |
|---|---|---|---|
| POST | `/api/auth/register` | Público (demo) | Crear usuario |
| POST | `/api/auth/login` | Público | Obtener JWT |
| GET | `/api/events` | JWT válido | Consultar eventos |
| POST/PUT/DELETE | `/api/events[/{id}]` | STAFF | Mantener eventos |
| POST | `/api/orders` | BUYER | Crear orden y encolar |
| GET | `/api/orders[/{id}]` | BUYER | Consultar órdenes propias |
| GET | `/api/tickets/mine` | BUYER | Consultar tickets emitidos |
| POST | `/api/tickets/validate/{code}` | STAFF | Validar una vez |

Las rutas `/internal/**` son exclusivas para comunicación entre servicios. En Compose solo se expone al computador el Gateway.

## Modelo y supuestos

- Cuatro microservicios: autenticación, eventos, órdenes y tickets. Cada uno usa su propia base PostgreSQL.
- Entidad evento: nombre, ubicación, fecha, aforo total y disponible. Orden: comprador, evento, cantidad y estado. Ticket: orden, evento, comprador, código y uso. Usuario: nombre, hash de contraseña y rol.
- La orden se guarda como pendiente antes de evaluar aforo. La reserva del evento es atómica e idempotente frente a reintentos. Tickets se emiten de forma idempotente por orden.
- El código de ticket es UUID. El primer uso de validación lo marca como usado.
- Se permite un máximo de 10 entradas por orden.
- Registro permite crear STAFF solo para facilitar la demo; en producción debe restringirse.

## Bases y credenciales locales

Se crean `eventpass_auth`, `eventpass_events`, `eventpass_orders`, `eventpass_tickets`. `docker-compose.yml` incluye claves de demostración locales: no reutilizar en producción ni subir secretos personales. Cambia `JWT_SECRET` antes de publicar en un repositorio público.

## Maven y JAR

Instala Java 21 y Maven; desde cada carpeta `eventpass-*/` ejecuta:

```powershell
mvn clean test package
```

El JAR aparece en `target/`. Compose compila los seis proyectos con una imagen multi-stage de Maven.

## GitHub

Carpetas preparadas como repositorios separados: los cuatro servicios, Gateway, procesador local e infraestructura (`eventpass-local-infra`). La carpeta de infraestructura debe clonarse junto a las otras seis carpetas para que Compose encuentre sus build contexts. Sigue [PUBLICAR_EN_GITHUB.md](PUBLICAR_EN_GITHUB.md) para crear las ramas `main`, `develop` y `feature/...` y subirlas manualmente.

## Video de evaluación

En 3–8 minutos: explicar componentes/capas; mostrar PostgreSQL; probar roles, evento, orden pendiente, resultado asíncrono, aforo concurrente, CRUD y validación doble (200 luego 409); ejecutar Maven y mostrar JAR; explicar clonación/README. Narración con voz de los estudiantes, sonido claro y flujo ordenado.


## Ejecutar pruebas unitarias e integración

### Pruebas unitarias (JUnit, no Postman)

Postman prueba los endpoints y la integración por HTTP; no ejecuta pruebas unitarias de Java. Para las unitarias, desde cada carpeta de servicio/procesador ejecuta:

```powershell
mvn clean test
```

Para compilar el JAR además de ejecutar los tests:

```powershell
mvn clean test package
```

Maven muestra `BUILD SUCCESS` si pasan. Los resultados detallados quedan en `target/surefire-reports/`. Se incluyeron pruebas básicas de JWT, regla de aforo, estado inicial de orden, código único de ticket y estructura del mensaje del procesador. El Gateway no tiene lógica de dominio unitaria propia.

### Pruebas de API e integración en Postman

1. Inicia los contenedores desde la raíz: `docker compose up --build`. Espera a que termine la inicialización; Postman usa el Gateway.
2. En Postman selecciona **Import** e importa solo `postman/EventPass_Local.postman_collection.json`.
3. Abre **Runner**, selecciona **EventPass - Pruebas locales** y pon un delay de **1000 ms** entre solicitudes. No necesitas importar environment ni copiar tokens.
4. Pulsa **Run**. La colección crea comprador/staff únicos, inicia sesión, crea y actualiza un evento, comprueba que BUYER no pueda administrar eventos (`403` esperado), crea una orden, consulta su estado repetidamente hasta 15 veces y luego lista y valida el ticket.
5. En **Test Results**, los `200`, `201`, `202`, `403` y `409` indicados son esperados. La segunda validación debe dar `409 Conflict`, demostrando que el ticket no se reutiliza.

La colección envía todas las solicitudes a través de `http://localhost:8080`, pero separa pruebas por carpeta: **Autenticación**, **Eventos**, **Órdenes/procesamiento asíncrono** y **Tickets**. Los microservicios no publican sus puertos al host en Compose, así que no cambies la URL a `8081`–`8084` en la configuración normal.

### Prueba adicional de aforo (manual en Postman)

1. Con token `STAFF`, crea un evento con `capacity: 1` y anota el ID.
2. Con token `BUYER`, crea dos órdenes separadas para ese evento, cada una con `quantity: 1`; ambas solicitudes deben responder `202 Accepted` inicialmente.
3. Espera a que el procesador consuma la cola; consulta `GET /api/orders` hasta que las dos órdenes salgan de `ACEPTADA_PENDIENTE`.
4. Una orden debe terminar `EMITIDA`, la otra `RECHAZADA`, y el evento debe quedar con `available: 0`. Esto demuestra que el aforo se descuenta de forma atómica.

Si una orden no cambia de estado, revisa los logs con `docker compose logs -f order-processor`, `docker compose logs -f localstack`, `docker compose logs -f events-service` y `docker compose logs -f tickets-service`. Para reintentar desde un estado limpio, `docker compose down -v` elimina la base local; úsalo solo si no necesitas conservar esos datos.


> ¿Quieres la versión corta? Empieza con [EMPEZAR_AQUI.md](EMPEZAR_AQUI.md): iniciar con doble clic, importar una sola colección y ejecutar pruebas.
