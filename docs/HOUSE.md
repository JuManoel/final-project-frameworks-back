## Casas (House)

### Criar casa

- **Endpoint:** `POST /house`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Body:**
  ```json
  {
    "description": "Apartamento com ótima localização",
    "addressData": {
      "street": "Rua X",
      "city": "Cidade Y",
      "state": "Estado Z",
      "number": "123",
      "complement": "Apto 45"
    }
  }
  ```
- **Resposta de sucesso:**  
  ```json
  {
    "addressData": {
      "street": "Rua X",
      "city": "Cidade Y",
      "state": "Estado Z",
      "number": "123",
      "complement": "Apto 45"
    },
    "description": "Apartamento com ótima localização"
  }
  ```
- **Notas:**  
  O usuário autenticado será definido como proprietário da casa.

---

### Listar casas (paginado)

- **Endpoint:** `GET /house`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Query params opcionais:**  
  `page`, `size`, `sort`
- **Resposta de sucesso:**  
  ```json
  {
    "content": [
      {
        "addressData": { ... },
        "description": "Apartamento ...",
        "ownerEmail": "dono@email.com",
        "ownerName": "Nome do Dono",
        "stars": 4.5,
        "id": 1
      },
      ...
    ],
    "totalPages": 1,
    "totalElements": 2,
    "last": true,
    "size": 15,
    "number": 0,
    "first": true,
    "numberOfElements": 2,
    "empty": false
  }
  ```

---

### Buscar casa por ID

- **Endpoint:** `GET /house/{id}`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Resposta de sucesso:**  
  ```json
  {
    "addressData": {
      "street": "Rua X",
      "city": "Cidade Y",
      "state": "Estado Z",
      "number": "123",
      "complement": "Apto 45"
    },
    "description": "Apartamento com ótima localização",
    "ownerEmail": "dono@email.com",
    "ownerName": "Nome do Dono",
    "stars": 4.5,
    "id": 1
  }
  ```

---

### Atualizar casa

- **Endpoint:** `PUT /house/{id}`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Body:**
  ```json
  {
    "description": "Nova descrição",
    "addressData": {
      "street": "Nova Rua",
      "city": "Nova Cidade",
      "state": "Novo Estado",
      "number": "456",
      "complement": "Apto 99"
    }
  }
  ```
- **Resposta de sucesso:**  
  ```json
  {
    "description": "Nova descrição",
    "addressData": {
      "street": "Nova Rua",
      "city": "Nova Cidade",
      "state": "Novo Estado",
      "number": "456",
      "complement": "Apto 99"
    }
  }
  ```
- **Notas:**  
  Apenas o proprietário da casa pode atualizar.

---

### Deletar casa

- **Endpoint:** `DELETE /house/{id}`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Resposta de sucesso:**  
  `"House deleted successfully"`
- **Notas:**  
  Apenas o proprietário da casa pode deletar.

---

## Notas Gerais

- Todos os endpoints de casa requerem autenticação com JWT.
- O token deve ser enviado no header:  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- Apenas o proprietário pode atualizar ou deletar sua casa.
- Para dúvidas, consulte os exemplos ou pergunte ao backend.