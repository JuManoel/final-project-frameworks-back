# FrameWorks JS - Backend

Backend do projeto final da matéria **FrameWorks JS**.  
Este backend serve como base para um sistema de gerenciamento e negociação de aparta-estúdios para estrangeiros (forâneos).  
O backend **não é obrigatório** para a matéria, mas foi desenvolvido para facilitar a integração e automação do sistema.

---

## 📚 Documentação

- [Autenticação](docs/AUTH.md)
- [Usuários](docs/USER.md)
- [Casas](docs/HOUSE.md) <!-- Crie este arquivo se desejar documentar casas -->
- [Avaliações](docs/REVIEW.md) <!-- Crie este arquivo se desejar documentar avaliações -->

---

## 🚀 Tecnologias

- Java 17
- Spring Boot
- Spring Security (JWT)
- JPA/Hibernate
- PostgreSQL / H2
- Lombok

---

## ℹ️ Sobre o Projeto

O sistema permite:

- Cadastro e autenticação de usuários (admin, cliente, proprietário)
- Gerenciamento de usuários
- Cadastro e gerenciamento de aparta-estúdios
- Avaliação de usuários e imóveis
- Segurança baseada em roles

---

## 🛠️ Como rodar o projeto localmente

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
   cd final-project-frameworks-back
   ```

2. **Instale as dependências:**
  ```bash
  ./mvnw clean install
  ```
  Ou, se você tiver o Maven instalado globalmente:
  ```bash
  mvn clean install
  ```

3. **Execute a aplicação:**
  - Usando o wrapper do Maven:
    ```bash
    ./mvnw spring-boot:run
    ```
  - Ou, com Maven global:
    ```bash
    mvn spring-boot:run
    ```
  - Ou, rode diretamente o JAR gerado:
    ```bash
    java -jar target/*.jar
    ```

4. **Acesse a API:**
  - Por padrão, a aplicação estará disponível em: [http://localhost:8080](http://localhost:8080)
  - Para detalhes sobre cada grupo de endpoints, acesse os links da documentação acima.
