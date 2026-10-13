@echo off
setlocal
cd /d "%~dp0"
docker compose down
echo EventPass se detuvo. Los datos locales se conservaron.
pause
