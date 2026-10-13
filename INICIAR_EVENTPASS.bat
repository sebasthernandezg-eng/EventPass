@echo off
setlocal
cd /d "%~dp0"
where docker >nul 2>&1
if errorlevel 1 (
  echo No encuentro Docker. Instala e inicia Docker Desktop primero.
  pause
  exit /b 1
)
docker compose version >nul 2>&1
if errorlevel 1 (
  echo Docker Compose no esta disponible. Actualiza Docker Desktop.
  pause
  exit /b 1
)
echo Iniciando EventPass. La primera vez puede tardar varios minutos.
echo Cuando aparezca http://localhost:8080, abre Postman y ejecuta la coleccion.
docker compose up --build
pause
