# 🍕 Algafood API
![badge-em-desenvolvimento](https://img.shields.io/badge/Status-Em%20Desenvolvimento-brightgreen?style=flat)

Projeto baseado em um sistema de delivery de comida. O projeto faz parte do curso Especialista Spring REST ministrado pela [AlgaWorks](https://www.algaworks.com/).

## Diagrama de Classes de Domínio
![diagrama-de-classes-de-dominio]()

## Conteúdo Abordado
- [x] Spring e Injeção de Dependências
- [x] Introdução ao JPA e Hibernate
- [x] REST com Spring
- [x] Super poderes do Spring Data JPA
- [x] Explorando mais do JPA e Hibernate
- [x] Pool de conexões e Flyway
- [x] Tratamento e modelagem de erros da API
- [x] Validações com Bean Validation
- [x] Testes de integração
- [x] Boas práticas e técnicas para APIs
- [x] Modelagem avançada e implementação da API
- [x] Modelagem de projeções, pesquisas e relatórios
- [x] Upload e download de arquivos
- [x] E-mails transacionais e Domain Events
- [x] CORS e consumo da API com JavaScript e Java
- [x] Cache de HTTP
- [ ] Documentação da API com OpenAPI, Swagger UI e SpringFox
- [x] Discoverability e HATEOAS - A Glória do REST
- [] Evoluindo e versionando a API
- [x] Logging
- [x] Segurança com Spring Security e OAuth2
- [x] OAuth2 avançado com JWT e controle de acesso
- [x] Dockerizando a aplicação
- [ ] Deploy em containers Docker na Amazon Web Services
- [ ] Documentação da API com SpringDoc
- [x] Spring Authorization Server

## 💻 Tecnologias Utilizadas
- Java 11
- Spring (Boot, Data JPA, HATEOAS, Security, Authorization Server, Thymeleaf, Docs)
- Hibernate
- MySQL 8
- Redis
- Flyway
- Swagger, OpenAPI e SpringDocs
- JUnit / Mockito / RestAssured / Selenium WebDriver
- ModelMapper
- Jasper Reports
- Docker / docker-compose
- OAuth2 com JWT

## 📖 Documentação (OA3)
![documentacao](#)

## ▶️ Como executar o projeto
Para executar o projeto é necessário ter o Java 11+, Maven e Docker instalados e configurados na sua máquina.

1. Clone o repositório na pasta desejada
```bash
git clone https://github.com/leandrobove/algafood-api.git
```

2. Vá até a raiz do projeto e execute
```bash
./mvnw package -DskipTests
```

3. Execute o docker-compose para subir os containers
```bash
docker-compose up
```

[Postman Collection](#)