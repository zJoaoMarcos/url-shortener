# URL Shortener

API REST para encurtamento de URLs construída com Spring Boot, Cassandra e Redis.

## Stack

- **Java 17** + **Spring Boot 4**
- **Apache Cassandra** — armazenamento persistente das URLs
- **Redis** — cache
- **Docker Compose** — infraestrutura local

## Como rodar

**1. Subir a infraestrutura**

```bash
docker-compose up -d
```

**2. Rodar a API**

```bash
cd api
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

## Endpoints

### Encurtar URL

```
POST /v1/shorter
Content-Type: application/json

{ "longUrl": "https://exemplo.com/url/muito/longa" }
```

Resposta:
```json
{ "shortUrl": "http://localhost:8080/Ab3xY9" }
```

### Redirecionar

```
GET /{shortcode}
```

Redireciona (`307`) para a URL original.

## Configuração

As configurações de Cassandra e Redis ficam em `api/src/main/resources/application.properties`.

| Propriedade | Padrão |
|---|---|
| `spring.cassandra.keyspace-name` | `spring_cassandra` |
| `spring.data.redis.host` | `localhost` |
| `spring.data.redis.port` | `6379` |
