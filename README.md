# 🚨 FireAway API

## Índice

1. [Tecnologias Utilizadas](#-tecnologias-utilizadas)  
2. [Entidades](#entidades)  
3. [Segurança](#-segurança)  
4. [Integração com Nominatim API](#-integração-com-nominatim-api)  
5. [Perfis de Usuário](#-perfis-de-usuário)  
6. [Como Executar o Projeto](#▶️-como-executar-o-projeto)  
7. [Configuração do JWT Secret](#✅-configuração-do-jwt-secret)  
8. [Configuração das Credenciais do Banco de Dados](#✅-configuração-das-credenciais-do-banco-de-dados)  
9. [Comandos para Executar a API](#✅-comandos-para-executar-a-api)  
10. [DevOps & Cloud Computing](#devops-&docker-computing)  
11. [Grupo Desenvolvedor](#-grupo-desenvolvedor)

---

# Sistema FireAway
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

---

## 🔒 Segurança

- Autenticação via JWT.
- Filtros personalizados: JwtTokenFilter para validar tokens em cada requisição.
- Tratamento de exceções: CustomAuthExceptionHandler para respostas padronizadas de erro (401 e 403).
- Controle de acesso via @PreAuthorize com perfis de usuário.

---

## 📡 Integração com Nominatim API
Utilizada para converter CEP em latitude e longitude, integrando dados geográficos aos alertas e monitoramentos. Responsável por auxiliar o método 
de listagem dos alertas próximos ao usuário.
URL da API:
🌐 https://nominatim.openstreetmap.org/search

---

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

---

## ▶️ Como Executar o Projeto
- Java 17 instalado
- Maven instalado
- Banco de Dados Oracle configurado
- Variável de ambiente para o JWT Secret configurada (JWT_SECRET)
- Dependências resolvidas (mvn clean install)

---

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

---

## ✅ Configuração do JWT Secret
Configure as credenciais do banco de dados para o funcionamento da aplicação:

```bash
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```

---

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

---

## 🔧☁️ DevOps & Cloud Computing

A aplicação conta com um Dockerfile para criação de imagem e um arquivo "docker-compose.yml" para criação de imagem e container
da API e do banco de dados Oracle.

Dentro do docker compose, certifique-se que o código do JWT é o mesmo rodando na aplicação:
```bash
JWT_SECRET={codigoBase64}
```

Para configuração do volume, verifique se você possui esse diretório criado na sua máquina:
```bash
C:/oracle-data:/opt/oracle/oradata
```
⚠️ Observação: caso você não queira configurar um volume, apenas retire do código


- Como realizar o teste?
Execute o comando no terminal:
```bash
docker compose up -d
```

Dentro do container do banco Oracle, crie um usuário com permissões para inserção de dados:
```bash
CREATE USER [nome-usuario] IDENTIFIED BY [senha-usuario];
GRANT CONNECT, RESOURCE TO [nome-usuario];
ALTER USER [nome-usuario] QUOTA UNLIMITED ON USERS;
exit;
```

- Scripts de requisições Teste

- Criação inicial de um usuário Administrador no container do banco:
```bash
INSERT INTO usuario (nome, email, cpf, senha, perfil, telefone) VALUES
('Jade', 'jade@fireaway.com', '44954401879', '$2a$12$FdnMsM92MUcTm5cJNuvco.A2B2qqCM1fw94lZUAePi5pR3N6ZJOzS', 'ADMINISTRADOR', '11945350242');
COMMIT;
```
⚠️ Para permissão de realização de outros métodos, é necessário se logar com o usuário cadastrado anteriormente.

- Exemplos de JSON usados para requisições POST:
Cadastro de usuário:
```bash
{
  "nome": "Gabriela",
  "email": "gabi@email.com",
  "cpf": "78541263256",
  "senha": "Marleygabi",
  "perfil": "SOCORRISTA",
  "telefone": "11987456321"

}
```

Cadastro de Sensor:
```bash
{
  "tipo": "TEMPERATURA",
  "latitude": "-255000.1",
  "longitude": "-85695.2",
  "status": "Sensor ativo"
}
```

Cadastro de Monitoramento:
```bash
{
  "sensorId": "1" {id_de_sensor_existente},
  "valor": "60.1",
  "descricao": "Monitoramento ativo"
}
```

Cadastro de usuário Morador:
```bash
{
  "nome": "Janaina",
  "email": "jana@email.com",
  "cpf": "45214563258",
  "senha": "janaina456",
  "endereco":{
      "logradouro": "Rua Pinheiros",
      "cidade": "sp",
      "estado": "sp",
      "cep": "01001-000",
  },
  "telefone": "1194563256"
}
```

Envio de mensagem:
```bash
{
  "emailReceptor": "jade@fireaway.com" {email_de_usuarios_existentes},
  "conteudo": "Olá, administrador! Gostaria de saber mais sobre as funcionalidades do sistema"
}
```

---

## 👥 Grupo Desenvolvedor
- Gabriela de Sousa Reis - RM558830
- Laura Amadeu Soares - RM556690
- Raphael Lamaison Kim - RM557914

