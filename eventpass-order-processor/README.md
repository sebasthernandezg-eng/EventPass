# EventPass Local Order Processor

Proceso Spring Boot que consulta SQS local y ejecuta el flujo asíncrono de órdenes: reserva el aforo, pide la emisión idempotente de tickets y cambia el estado de la orden a `EMITIDA` o `RECHAZADA`. Al fallar una llamada, no borra el mensaje para permitir que SQS lo reintente.

Este componente **simula localmente** la tarea de una Lambda; no es una función AWS desplegada.

## Ejecutar

Se inicia junto con `docker compose up --build` desde la raíz EventPass. Directo necesita Java 21, Maven, LocalStack SQS y variables `SQS_ENDPOINT`, `SQS_QUEUE_URL`, `EVENTS_URL`, `ORDERS_URL`, `TICKETS_URL`; ejecuta `mvn clean test package` y `mvn spring-boot:run`.

Consulta el README principal para levantar la cola y probar el recorrido completo.
