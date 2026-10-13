# EventPass Local Infrastructure

Docker Compose para ejecutar PostgreSQL (cuatro bases), LocalStack SQS, Gateway y los microservicios. Requiere Docker Desktop con Compose v2.

## Organización de repos

Clona esta carpeta como `eventpass-local-infra` y, en la misma carpeta padre, clona también los repos `eventpass-auth-service`, `eventpass-events-service`, `eventpass-orders-service`, `eventpass-tickets-service`, `eventpass-order-processor` y `eventpass-gateway`. El `docker-compose.yml` de aquí espera esas carpetas hermanas.

```powershell
docker compose up --build
```

La API de usuario queda en `http://localhost:8080`. Para apagar: `docker compose down`. Para borrar bases/volumen local de prueba: `docker compose down -v` (elimina datos).

Las credenciales y el JWT incluidos son únicamente de demostración local. No desplegar este archivo a producción. El Gateway sustituye API Gateway y el consumidor Java sustituye Lambda para la demostración sin AWS; SQS se ejecuta localmente con LocalStack.

Ver el README principal del paquete EventPass para el flujo Postman, endpoints y supuestos del modelo.
