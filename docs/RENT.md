## Aluguéis (Rent)

### Criar Aluguel

- **Endpoint:** `POST /rent`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Body:**
  ```json
  {
    "houseId": 1,
    "price": 250000,
    "locator": "email@inquilino.com"
  }
  ```
- **Resposta de sucesso:**
  ```json
  {
    "id": 1,
    "houseId": 1,
    "locator": "email@inquilino.com",
    "active": true
  }
  ```
- **Notas:**
  - Apenas proprietários podem criar aluguéis
  - Não é possível alugar sua própria casa
  - Um usuário não pode ter mais de um aluguel ativo

### Listar Aluguéis (Inquilino)

- **Endpoint:** `GET /rent/locator`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Query params opcionais:**  
  `page`, `size`
- **Resposta de sucesso:**
  ```json
  {
    "content": [
      {
        "id": 1,
        "houseId": 1,
        "locator": "email@inquilino.com",
        "active": true
      }
    ],
    "totalPages": 1,
    "totalElements": 1,
    "last": true,
    "size": 10,
    "number": 0,
    "first": true,
    "numberOfElements": 1,
    "empty": false
  }
  ```

### Listar Aluguéis (Proprietário)

- **Endpoint:** `GET /rent/owner`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Query params opcionais:**  
  `page`, `size`
- **Resposta de sucesso:**
  ```json
  {
    "content": [
      {
        "id": 1,
        "houseId": 1,
        "locator": "email@inquilino.com",
        "active": true
      }
    ],
    "totalPages": 1,
    "totalElements": 1,
    "last": true,
    "size": 10,
    "number": 0,
    "first": true,
    "numberOfElements": 1,
    "empty": false
  }
  ```

### Aceitar/Rejeitar Aluguel

- **Endpoint:** `PUT /rent/accept`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Body:**
  ```json
  {
    "id": 1,
    "accepted": true
  }
  ```
- **Resposta de sucesso:**
  ```json
  {
    "id": 1,
    "houseId": 1,
    "locator": "email@inquilino.com",
    "active": true
  }
  ```
- **Notas:**
  - Apenas o inquilino pode aceitar/rejeitar o aluguel
  - A casa deve estar disponível
  - O inquilino não pode ter outro aluguel ativo

### Cancelar Aluguel

- **Endpoint:** `DELETE /rent/{id}`
- **Headers:**  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- **Notas:**
  - Apenas o proprietário pode cancelar o aluguel
  - A casa volta a ficar disponível após o cancelamento

## Notas Gerais

- Todos os endpoints requerem autenticação com JWT
- O token deve ser enviado no header:  
  `Authorization: Bearer JWT_TOKEN_AQUI`
- Um usuário não pode ter mais de um aluguel ativo ao mesmo tempo
- Uma casa não pode ter mais de um aluguel ativo ao mesmo tempo
- Para dúvidas, consulte os exemplos ou pergunte ao backend