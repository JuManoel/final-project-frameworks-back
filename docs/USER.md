## Usuarios

### Crear usuario

- **Endpoint:** `POST /user`
- **Body:**
  ```json
  {
    "name": "Nombre del Usuario",
    "email": "usuario@email.com",
    "password": "SuContraseña",
    "typeUser": "ADMIN|CLIENT|OWENER"
  }
  ```
- **Respuesta exitosa:**  
  `"User created successfully"`
- **Notas:**  
  No requiere autenticación.

---

### Obtener usuario por email

- **Endpoint:** `GET /user/{email}`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Respuesta exitosa:**
  ```json
  {
    "name": "Nombre del Usuario",
    "email": "usuario@email.com",
    "stars": 4.5,
    "typeUser": "ADMIN|CLIENT|OWENER"
  }
  ```

---

### Actualizar datos del usuario

- **Endpoint:** `PUT /user/update`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Body:**
  ```json
  {
    "newName": "Nuevo Nombre",
    "email": "usuario@email.com",
    "newEmail": "nuevo@email.com"
  }
  ```
- **Respuesta exitosa:**  
  ```json
  {
    "newName": "Nuevo Nombre",
    "email": "usuario@email.com",
    "newEmail": "nuevo@email.com"
  }
  ```

---

### Actualizar contraseña

- **Endpoint:** `PUT /user/update/password`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Body:**
  ```json
  {
    "password": "ContraseñaActual",
    "newPassword": "NuevaContraseña"
  }
  ```
- **Respuesta exitosa:**  
  `"Password updated successfully"`

---

### Eliminar usuario

- **Endpoint:** `DELETE /user`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Respuesta exitosa:**  
  `"User deleted successfully"`

---

## Notas Generales

- Todos los endpoints (excepto crear usuario y login) requieren autenticación con JWT.
- El token debe enviarse en el header:  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- Los campos y tipos de usuario válidos son: `ADMIN`, `CLIENT`, `OWENER`.
- Si tienes dudas, revisa los ejemplos o pregunta al backend.