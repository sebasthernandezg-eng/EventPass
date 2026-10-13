@echo off
setlocal
cd /d "%~dp0"
where mvn >nul 2>&1
if errorlevel 1 (
  echo Maven no esta instalado o no esta en PATH.
  echo Instala Java 21 y Maven; luego vuelve a ejecutar este archivo.
  pause
  exit /b 1
)
for %%D in (eventpass-auth-service eventpass-events-service eventpass-orders-service eventpass-tickets-service eventpass-order-processor eventpass-gateway) do (
  echo.
  echo ===== Probando %%D =====
  pushd "%%D"
  call mvn -q clean test
  if errorlevel 1 (
    echo.
    echo ERROR: fallaron las pruebas en %%D.
    popd
    pause
    exit /b 1
  )
  popd
)
echo.
echo Todas las pruebas unitarias terminaron correctamente.
echo Resultados detallados: carpeta target\surefire-reports de cada modulo.
pause
