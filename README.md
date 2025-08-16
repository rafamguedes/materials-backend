# 📦 Materials API

Uma API REST para gerenciamento de materiais e reservas, desenvolvida com Spring Boot. O sistema permite o controle eficiente de itens, usuários e reservas com funcionalidades avançadas de segurança, cache e relatórios.

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3.4.5**
- **Spring Security** (Autenticação JWT)
- **Spring Data JPA** (Hibernate)
- **PostgreSQL** (Banco de dados principal)
- **Redis** (Cache e sessões)
- **Liquibase** (Controle de versão do banco)
- **Docker & Docker Compose**
- **Maven** (Gerenciamento de dependências)
- **Swagger/OpenAPI** (Documentação da API)
- **Thymeleaf** (Templates para relatórios)
- **SendGrid** (Envio de emails)
- **Nginx** (Load balancer)

## 📋 Funcionalidades

### 🔐 Autenticação e Autorização
- Login com JWT tokens
- Refresh tokens
- Rate limiting
- Controle de acesso baseado em roles

### 👥 Gerenciamento de Usuários
- CRUD completo de usuários
- Perfis com endereços
- Paginação e filtros avançados
- Relatórios de usuários em PDF

### 📦 Gerenciamento de Itens
- Cadastro de materiais com tipos e status
- Números de série únicos
- Controle de disponibilidade
- Relatórios de inventário

### 📅 Sistema de Reservas
- Agendamento de materiais
- Controle de status (Ativa, Finalizada, Cancelada)
- Códigos únicos de reserva
- Relatórios de reservas
- Notificações por email

### 📊 Relatórios e Monitoramento
- Relatórios em PDF com Thymeleaf
- Health checks
- Métricas com Spring Actuator
- Logs estruturados

## 🏗️ Arquitetura

```
src/
├── main/java/com/materials/api/
│   ├── config/          # Configurações (ModelMapper, Swagger, etc.)
│   ├── controller/      # Controllers REST
│   ├── entity/          # Entidades JPA
│   ├── enums/           # Enumerações
│   ├── pagination/      # DTOs de paginação
│   ├── repository/      # Repositórios JPA
│   ├── security/        # Configurações de segurança
│   ├── service/         # Lógica de negócio
│   └── utils/           # Utilitários
└── resources/
    ├── application*.properties
    ├── db/changelog/    # Scripts Liquibase
    └── templates/       # Templates Thymeleaf
```

## 🐳 Como Executar

### Pré-requisitos
- Docker e Docker Compose
- Java 17+ (para desenvolvimento local)
- Maven 3.6+ (para desenvolvimento local)

### 1. Usando Docker (Recomendado)

```bash
# Clone o repositório
git clone https://github.com/rafamguedes/api-materials.git
cd api-materials

# Navegue até a pasta docker
cd docker

# Execute o ambiente completo
docker compose up -d --build

# Verifique os containers
docker compose ps
```

O sistema estará disponível em:
- **API**: http://localhost:80
- **Swagger UI**: http://localhost:80/swagger-ui.html
- **PostgreSQL**: localhost:5432
- **Redis**: localhost:6379

### 2. Desenvolvimento Local

```bash
# Clone o repositório
git clone https://github.com/rafamguedes/api-materials.git
cd api-materials

# Execute apenas o banco de dados
cd docker
docker compose up postgres redis -d

# Volte para a raiz e execute a aplicação
cd ..
./mvnw spring-boot:run
```

### 3. Parar os Serviços

```bash
cd docker
docker compose down

# Para limpar completamente (cuidado: remove volumes)
docker compose down --rmi all --volumes --remove-orphans
```

## 📖 Documentação da API

A documentação completa da API está disponível via Swagger UI após executar a aplicação:

**http://localhost:80/swagger-ui.html**

### Principais Endpoints

#### Autenticação
- `POST /auth/login` - Login
- `POST /auth/refresh` - Renovar token

#### Usuários
- `GET /users` - Listar usuários (paginado)
- `POST /users` - Criar usuário
- `PUT /users/{id}` - Atualizar usuário
- `DELETE /users/{id}` - Remover usuário

#### Itens
- `GET /items` - Listar itens (paginado)
- `POST /items` - Criar item
- `PUT /items/{id}` - Atualizar item
- `DELETE /items/{id}` - Remover item

#### Reservas
- `GET /reservations` - Listar reservas (paginado)
- `POST /reservations` - Criar reserva
- `PUT /reservations/{id}/status` - Atualizar status

#### Relatórios
- `GET /reports/users` - Relatório de usuários (PDF)
- `GET /reports/items` - Relatório de itens (PDF)
- `GET /reports/reservations` - Relatório de reservas (PDF)

## ⚙️ Configuração

### Variáveis de Ambiente

As principais configurações podem ser ajustadas através de variáveis de ambiente:

```bash
# Banco de dados
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/materials_db
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=root

# Redis
SPRING_REDIS_HOST=redis
SPRING_REDIS_PORT=6379
SPRING_REDIS_PASSWORD=redispass

# JWT
SPRING_SECURITY_TOKEN_SECRET=your-secret-key
SPRING_SECURITY_TOKEN_EXPIRATION=60

# SendGrid
SENDGRID_API_KEY=your-sendgrid-key
SENDGRID_EMAIL_FROM=your-email@domain.com
```

### Perfis de Ambiente

- **dev**: Desenvolvimento (application-dev.properties)
- **prod**: Produção (application-prod.properties)

## 🧪 Testes

```bash
# Executar todos os testes
./mvnw test

# Executar com relatório de cobertura
./mvnw test jacoco:report
```

## 🔒 Segurança

- **JWT Authentication**: Tokens seguros com expiração configurável
- **Rate Limiting**: Proteção contra ataques de força bruta
- **CORS**: Configurado para ambientes específicos
- **Validação**: Validação robusta de entrada de dados
- **Logs**: Auditoria de ações sensíveis

## 📈 Monitoramento

### Health Checks
- `GET /actuator/health` - Status da aplicação
- `GET /actuator/metrics` - Métricas da aplicação
- `GET /actuator/info` - Informações da aplicação

### Cache
- Redis configurado para cache de consultas frequentes
- TTL configurável por tipo de dado
- Invalidação automática em atualizações
