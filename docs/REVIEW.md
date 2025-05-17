## Avaliações (Reviews)

### Criar avaliação para usuário

- **Endpoint:** `POST /review/user`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Body:**
  ```json
  {
    "userReviewed": "email_do_usuario_avaliado@email.com",
    "comment": "Ótimo usuário, recomendo!",
    "stars": 5
  }
  ```
- **Resposta de sucesso:**
  ```json
  {
    "email": "email_de_quem_avaliou@email.com",
    "name": "Nome de quem avaliou",
    "comment": "Ótimo usuário, recomendo!",
    "stars": 5,
    "postedDate": "2024-05-17T12:34:56"
  }
  ```
- **Notas:**  
  O usuário autenticado será considerado o autor da avaliação.

---

### Listar avaliações de um usuário

- **Endpoint:** `GET /review/user/{email}`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Query params opcionais:**  
  `page`, `size`
- **Resposta de sucesso:**
  ```json
  {
    "content": [
      {
        "email": "email_de_quem_avaliou@email.com",
        "name": "Nome de quem avaliou",
        "comment": "Ótimo usuário, recomendo!",
        "stars": 5,
        "postedDate": "2024-05-17T12:34:56"
      }
      // ...mais avaliações
    ],
    "totalPages": 1,
    "totalElements": 2,
    "last": true,
    "size": 10,
    "number": 0,
    "first": true,
    "numberOfElements": 2,
    "empty": false
  }
  ```

---

### Criar avaliação para casa

- **Endpoint:** `POST /review/house`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Body:**
  ```json
  {
    "houseId": 1,
    "comment": "Casa muito confortável e bem localizada.",
    "stars": 4
  }
  ```
- **Resposta de sucesso:**
  ```json
  {
    "email": "email_de_quem_avaliou@email.com",
    "name": "Nome de quem avaliou",
    "comment": "Casa muito confortável e bem localizada.",
    "stars": 4,
    "postedDate": "2024-05-17T12:34:56"
  }
  ```
- **Notas:**  
  O usuário autenticado será considerado o autor da avaliação.

---

### Listar avaliações de uma casa

- **Endpoint:** `GET /review/house/{id}`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Query params opcionais:**  
  `page`, `size`
- **Resposta de sucesso:**
  ```json
  {
    "content": [
      {
        "email": "email_de_quem_avaliou@email.com",
        "name": "Nome de quem avaliou",
        "comment": "Casa muito confortável e bem localizada.",
        "stars": 4,
        "postedDate": "2024-05-17T12:34:56"
      }
      // ...mais avaliações
    ],
    "totalPages": 1,
    "totalElements": 2,
    "last": true,
    "size": 10,
    "number": 0,
    "first": true,
    "numberOfElements": 2,
    "empty": false
  }
  ```

---

## Notas Gerais

- Todos os endpoints de avaliação requerem autenticação com JWT.
- O token deve ser enviado no header:  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- As avaliações podem ser feitas apenas por usuários autenticados.
- Para dúvidas, consulte os exemplos ou pergunte ao backend.