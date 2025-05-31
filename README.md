# 🚨 FireAway API
FireAway API é uma aplicação backend RESTful desenvolvida em Java com Spring Boot, responsável pela gestão de usuários, autenticação via JWT, 
controle de alertas ambientais (com localização via latitude e longitude) e integração com sensores. Seu desenvolvimento se deve a estruturação
do funcionamento do sistema FireAway.


## 🛠️ Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- Banco de Dados: Oracle
- Lombok
- Swagger (OpenAPI) para documentação
- BCrypt
- Nominatim API para geolocalização


## Entidades
- 👤 Usuário
- 📟 Sensor
- 📊 Monitoramento
- 🚨 Alerta
- 💬 Mensagem

## 🔒 Segurança

- Autenticação via JWT.
- Filtros personalizados: JwtTokenFilter para validar tokens em cada requisição.
- Tratamento de exceções: CustomAuthExceptionHandler para respostas padronizadas de erro (401 e 403).
- Controle de acesso via @PreAuthorize com perfis de usuário.

## 📡 Integração com Nominatim API
Utilizada para converter CEP em latitude e longitude, integrando dados geográficos aos alertas e monitoramentos. Responsável por auxiliar o método 
de listagem dos alertas próximos ao usuário.
URL da API:
🌐 https://nominatim.openstreetmap.org/search


## 👥 Perfis de Usuário

### MORADOR
- Descrição: Usuário comum do sistema, responsável por gerenciar suas próprias informações e receber alertas próximos sobre sensores e situações de risco.
- Permissões:
Cadastrar-se como Morador (POST /usuario/cadastro/morador)
Atualizar seus próprios dados (PUT /usuario/atualiza)
Consultar alertas próximos gerados pelos monitoramentos dos sensores cadastrados (POST /alertas/proximos)
Enviar mensagens para outros usuários (POST /mensagem/enviar)
Consultar mensagens recebidas (GET /mensagem/recebidas)

### SOCORRISTA
- Descrição: Usuário presente no sistema para auxiliar no socorro, caso seja detectado condições iniciais de incêndio. Responsável por acompanhar e gerenciar monitoramentos e alertas.
- Permissões:
Consultar todos os alertas gerados por monitoramento (GET /alertas/todos)
Consultar alertas por ID (GET /alertas/{id})
Deletar alertas desnecessários (DELETE /alertas/{id})
Consultar todos e por ID os monitoramentos dos sensores (GET /monitoramento) - (GET /monitoramento/{id})
Consultar todos os sensores e por ID cadastrado (GET /sensor) - (GET /sensor/{id})
Enviar mensagens para outros usuários (POST /mensagem/enviar)
Consultar mensagens recebidas (GET /mensagem/recebidas)

### ADMINISTRADOR
- Descrição: Usuário com todas as permissões de funcionalidade. 
- Permissões: Responsável pelo gerenciamento completo, incluindo cadastro entidades e monitoramento completo.


## ▶️ Como Executar o Projeto
- Java 17 instalado
- Maven instalado
- Banco de Dados Oracle configurado
- Variável de ambiente para o JWT Secret configurada (JWT_SECRET)
- Dependências resolvidas (mvn clean install)


## ✅ Configuração do JWT Secret

Antes de iniciar a aplicação, configure a variável de ambiente necessária para assinatura e validação dos tokens JWT:
```bash
export JWT_SECRET=seu_token_secreto
```

Ou, se preferir, adicione no application.properties:
```bash
jwt.secret=seu_token_secreto
```
⚠️ IMPORTANTE: sem o JWT_SECRET, a autenticação não funcionará!

## ✅ Comandos para executar a API

1️⃣ Clonar o repositório:
```bash
git clone https://github.com/Global-Solution-1/GS-FireAwaySystem.git
cd GS-FireAwaySystem
```

2️⃣ Construir o projeto:
```bash
mvn clean install
```

3️⃣ Rodar a aplicação:
```bash
mvn spring-boot:run
```
✅ A API estará disponível em:
http://localhost:8080

✅ Acessar documentação Swagger:
📖 http://localhost:8080/swagger-ui.html
ou
📖 http://localhost:8080/swagger-ui/index.html


## 👥 Grupo Desenvolvedor
- Gabriela de Sousa Reis - RM558830
- Laura Amadeu Soares - RM556690
- Raphael Lamaison Kim - RM557914

