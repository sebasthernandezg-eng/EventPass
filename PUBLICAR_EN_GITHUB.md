# Publicar EventPass en GitHub

La conexión de GitHub no quedó autorizada en esta sesión, por lo que el paquete está preparado localmente pero **no se han creado ni subido repositorios a tu cuenta**. Para compartirlo, publica cada carpeta como repositorio independiente (confirma con tu docente si prefiere otro formato):

- `eventpass-auth-service`
- `eventpass-events-service`
- `eventpass-orders-service`
- `eventpass-tickets-service`
- `eventpass-gateway`
- `eventpass-order-processor`
- `eventpass-local-infra`

## Por cada carpeta

1. En GitHub, crea un repositorio vacío con el mismo nombre. No agregues README ni `.gitignore` en el sitio porque ya están en la carpeta.
2. Abre PowerShell en la carpeta del servicio y configura tu identidad de Git si aún no lo has hecho:

```powershell
git config --global user.name "TU NOMBRE"
git config --global user.email "TU CORREO"
```

3. Inicializa y publica el código (reemplaza el nombre y URL):

```powershell
git init
git add .
git commit -m "chore: crear estructura inicial de EventPass"
git branch -M main
git remote add origin https://github.com/TU-USUARIO/NOMBRE-REPO.git
git push -u origin main
git checkout -b develop
git push -u origin develop
git checkout -b feature/implementacion-inicial
git push -u origin feature/implementacion-inicial
```

Después, los cambios de trabajo van en ramas `feature/...` y se integran mediante Pull Request a `develop`; al terminar, integra `develop` a `main`. Usa mensajes de commit específicos, por ejemplo `feat: validar ticket de un solo uso`.

No compartas claves reales, tokens, `.env` ni datos personales. Si el repositorio será privado, da acceso al docente. Entrega los enlaces de todos los repositorios en AVA y envía copia por correo conforme a la pauta.
