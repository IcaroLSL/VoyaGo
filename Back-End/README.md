# VoyaGo — Back-End

Visão geral
-----------
O back-end do VoyaGo é responsável por:
- Autenticação de usuários (registro/login).
- Armazenamento de lugares (places) e roteiros (itineraries).
- Gerar roteiros a partir de parâmetros (destinos, datas, preferências).

Tecnologias
-----------
- Java 17+
- Spring Boot
- Spring Web, Spring Data JPA
- PostgreSQL (para os usuarios) e SqlServer (dados)
- Maven (ou Gradle se preferir)

Configuração (variáveis de ambiente)
----------------------------------
- `SPRING_DATASOURCE_URL` — URL JDBC do banco (ex: `jdbc:postgresql://localhost:5432/voyago`)
- `SPRING_DATASOURCE_USERNAME` — usuário do DB
- `SPRING_DATASOURCE_PASSWORD` — senha do DB
- `SPRING_PROFILES_ACTIVE` — profile ativo (`dev` para H2 ou `prod` para Postgres)

Execução em desenvolvimento
---------------------------
Assumindo Maven:

1. Entre na pasta `Back-End/`.
2. Para rodar com o profile `dev` (H2 em memória):

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

3. Para compilar e gerar o JAR:

```bash
mvn clean package
java -jar target/voyago-backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

Banco de dados e migrations
---------------------------
Recomenda-se usar Flyway para migrations. Migrations ficam em `src/main/resources/db/migration`.

Endpoints principais (exemplos)
------------------------------
- GET /actuator/health — healthcheck

- POST /api/auth/register
  - Request:
    ```json
    {"name":"Usuário","email":"user@example.com","password":"senha"}
    ```
  - Response: 201 Created (user data sem senha)

- POST /api/auth/login
  - Request:
    ```json
    {"email":"user@example.com","password":"senha"}
    ```
  - Response: 200 OK com token (JWT) ou sessão

- GET /api/places?query={text}&lat={lat}&lng={lng}
  - Busca lugares básicos (pode usar integração com APIs externas como Google Places/Here)

- POST /api/itineraries/generate
  - Request (exemplo):
    ```json
    {
      "userId": 1,
      "destinations": ["Rio de Janeiro","Paraty"],
      "startDate":"2026-01-10",
      "endDate":"2026-01-15",
      "preferences": {"activityType":"cultural","pace":"moderate"}
    }
    ```
  - Response:
    ```json
    {
      "itineraryId": 42,
      "days": [ {"date":"2026-01-10","activities":[...]}, ... ]
    }
    ```

- GET /api/itineraries/{id} — retorna roteiros salvos

Testes
------
Adicionar testes unitários para o serviço gerador de roteiros e testes de integração (usando H2) para endpoints.

Notas e próximos passos
----------------------
- Implementar autenticação JWT e roles (opcional).
- Integrar com APIs de lugares (Google Places, OpenStreetMap/Nominatim) para enriquecer dados de `Place`.
- Implementar cache quando necessário (Redis) e fila para geração de roteiros assíncrona (RabbitMQ/Kafka) se ganhar escala.

