# API de Usuarios - Documentación para Frontend

Esta API permite gestionar usuarios en el sistema. A continuación se describen los endpoints disponibles, el formato de las peticiones y las respuestas esperadas.

---

## **Autenticación**

- **Login**
  - **Endpoint:** `POST /login`
  - **Body:**
    ```json
    {
      "email": "usuario@email.com",
      "password": "SuContraseña"
    }
    ```
  - **Respuesta :exitosa**
    ```json
    {
      "token": "JWT_TOKEN_AQUI",
      "email": "usuario@email.com",
      "name": "Nombre del Usuario",
      "typeUser": "ADMIN|CLIENT|OWENER"
    }
    ```
  - **Notas:**  
    Guarda el [token](http://_vscodecontentref_/0) recibido. Debes enviarlo en el header `Authorization` como `Bearer JWT_TOKEN_AQUI` para acceder a los endpoints protegidos.

---

## **Usuarios**

- **Crear usuario**
  - **Endpoint:** [POST /user](http://_vscodecontentref_/1)
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

- **Obtener usuario por email**
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

- **Actualizar datos del usuario**
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

- **Actualizar contraseña**
  - **Endpoint:** `PUT /user/update/password`
  - **Headers:**  
    `Authorization: Bearer JWT_TOKEN_AQUI`
  - **Body:**
    ```json
    {
      "email": "usuario@email.com",
      "password": "ContraseñaActual",
      "newPassword": "NuevaContraseña"
    }
    ```
  - **Respuesta exitosa:**  
    `"Password updated successfully"`

---

- **Eliminar usuario**
  - **Endpoint:** `DELETE /user/{email}`
  - **Headers:**  
    `Authorization: Bearer JWT_TOKEN_AQUI`
  - **Respuesta exitosa:**  
    `"User deleted successfully"`

---

## **Notas Generales**
- Todos los endpoints (excepto crear usuario y login) requieren autenticación con JWT.
- El token debe enviarse en el header:  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- Los campos y tipos de usuario válidos son: `ADMIN`, `CLIENT`, `OWENER`.

---

Cualquier duda, revisa los ejemplos o pregunta al backend.