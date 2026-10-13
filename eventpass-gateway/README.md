# EventPass API Gateway local

Gateway Spring Cloud que enruta las solicitudes de usuario a los cuatro servicios. Puerto host `8080`; rutas `/api/auth/**`, `/api/events/**`, `/api/orders/**` y `/api/tickets/**`. Los servicios de dominio validan el JWT y los roles.

## Ejecutar

Usa `docker compose up --build` desde la raíz EventPass. Para iniciar manualmente, Java 21 + Maven y servicios disponibles en nombres/puertos definidos en `src/main/resources/application.yml`; ejecuta `mvn clean test package` y `mvn spring-boot:run`.

La entrada recomendada para Postman es `http://localhost:8080`. Gateway local sustituye Amazon API Gateway únicamente para demostrar el sistema sin conexión a AWS.
