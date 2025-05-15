# FrameWorksJs - Backend API

Bienvenido al backend del proyecto **FrameWorksJs**.  
Esta API RESTful permite la gestión de usuarios, autenticación y otras entidades del sistema.  
Desarrollada con Spring Boot, JPA y JWT para autenticación segura.

---

## 📄 Página 1: Descripción General

- **Tecnologías:** Java 17, Spring Boot, Spring Security, JPA, JWT, PostgreSQL/H2.
- **Funcionalidad principal:**  
  - Registro y autenticación de usuarios.
  - Gestión de usuarios (crear, consultar, actualizar, eliminar).
  - Seguridad basada en roles (`ADMIN`, `CLIENT`, `OWENER`).
- **Autenticación:**  
  - Basada en JWT.  
  - El token se obtiene al hacer login y se debe enviar en el header `Authorization` para acceder a rutas protegidas.

---

## 📄 Página 2: Autenticación

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
    "typeUser": "ADMIN|CLIENT|OWENER"
  }
  ```
- **Notas:**  
  Guarda el token recibido. Debes enviarlo en el header `Authorization` como `Bearer JWT_TOKEN_AQUI` para acceder a los endpoints protegidos.

---

## 📄 Página 3: Usuarios

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
    "email": "usuario@email.com",
    "password": "ContraseñaActual",
    "newPassword": "NuevaContraseña"
  }
  ```
- **Respuesta exitosa:**  
  `"Password updated successfully"`

---

### Eliminar usuario

- **Endpoint:** `DELETE /user/{email}`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Respuesta exitosa:**  
  `"User deleted successfully"`

---

## 📄 Página 4: Notas Generales

- Todos los endpoints (excepto crear usuario y login) requieren autenticación con JWT.
- El token debe enviarse en el header:  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- Los campos y tipos de usuario válidos son: `ADMIN`, `CLIENT`, `OWENER`.
- Si tienes dudas, revisa los ejemplos o pregunta al backend.

---