## Autenticación

### Login

- **Endpoint:** `POST /login`
- **Body:**
  ```json
  {
    "email": "usuario@email.com",
    "password": "SuContraseña"
  }
  ```
- **Respuesta exitosa:**
  ```json
  {
    "token": "JWT_TOKEN_AQUI",
    "email": "usuario@email.com",
    "name": "Nombre del Usuario",
    "typeUser": "ADMIN|CLIENT|OWNER"
  }
  ```
- **Notas:**  
  Guarda el token recibido. Debes enviarlo en el header `Authorization` como `Bearer JWT_TOKEN_AQUI` para acceder a los endpoints protegidos.

---

## Notas Generales

- Todos los endpoints (excepto crear usuario y login) requieren autenticación con JWT.
- El token debe enviarse en el header:  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- Los campos y tipos de usuario válidos son: `ADMIN`, `CLIENT`, `OWNER`.
- Si tienes dudas, revisa los ejemplos o pregunta al backend.
