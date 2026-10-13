# EventPass: empezar aquí

## Lo mínimo para probarlo

1. **Descomprime** `EventPass_Local.zip` en tu computador.
2. Instala y abre **Docker Desktop**.
3. Haz doble clic en **`INICIAR_EVENTPASS.bat`**. Espera hasta que los servicios hayan iniciado; deja esa ventana abierta.
4. Abre Postman y selecciona **Import**. Importa solo `postman/EventPass_Local.postman_collection.json`.
5. En Postman abre la colección **EventPass - Pruebas locales**, pulsa **Run**, deja **1000 ms** de espera y pulsa **Run EventPass - Pruebas locales**. No necesitas importar environment ni escribir tokens: la colección se encarga de eso.
6. Para apagar EventPass, haz doble clic en **`DETENER_EVENTPASS.bat`**.

## Pruebas unitarias Java

Si además quieres ejecutar las pruebas unitarias, instala Java 21 y Maven y haz doble clic en **`EJECUTAR_TESTS.bat`**. Las pruebas unitarias se ejecutan con Maven; Postman ejecuta las pruebas de API e integración.

## Qué debes ver en Postman

- Registra e inicia sesión como comprador y staff.
- Crea y consulta un evento.
- Comprueba que un comprador no pueda crear eventos (el `403` es esperado).
- Crea una orden y espera que cambie de pendiente a emitida.
- Consulta el ticket: validarlo una vez responde `200`, repetirlo responde `409`.

La dirección local es `http://localhost:8080`. Si es la primera ejecución, la descarga y compilación de imágenes Docker puede tardar varios minutos.
