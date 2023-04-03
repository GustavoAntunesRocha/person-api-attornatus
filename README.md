
<h3 align="center">Api de gerencimanto de pessoas</h3>

---

## 📝 Table of Contents

- [Sobre](#about)
- [Deployment](#deployment)
- [Testes](#tests)
- [Como usar](#usage)
- [Tecnologias utilizadas](#built_using)
- [Autores](#authors)

<br>

---

## 🧐 Sobre <a name = "about"></a>

<p>Esse é um projeto para um teste técnico para a empresa Attornatus.
</p>

---

## 🎈 Como usar <a name="usage"></a>

### Swagger doc:

/swagger-ui/index.html

<br>

### Base URL:
<br>

### /api/pessoa
<br>

| Metodo | Parâmetros | Descrição |
|---|---|---|
| `GET` | id (integer) ou nome (string) | Retorna os dados de pessoas. Se nenhum parametro for passado, retorna todas. |
| `GET /enderecos` | idPessoa | Retorna os endereços de uma pessoa. |
| `POST` | | Cria um novo objeto do tipo pessoa. |
| `POST /addEndereco` | idPessoa (integer) e objeto Json de um endereço | Adiciona um endereço à uma pessoa. |
| `PUT` | | Atualiza os dados de uma pessoa. |
| `PUT /enderecoPrincipal` | idEndereco(integer) e idPessoa(integer) | Coloca um endereco como sendo o principal de uma pessoa. |
| `DELETE` | id(integer) | Apaga os dados de uma pessoa. |

<br>

---

## 🚀 Deployment <a name = "deployment"></a>

```
./mvnw spring-boot:run
```
---

## 🔧 Running the tests <a name = "tests"></a>

Esse projeto possuiu testes unitários e de integração.

Para rodar os testes:

```
./mvnw test
```
---

## ⛏️ Built Using <a name = "built_using"></a>

- [JDK 20](https://jdk.java.net/20/) - Java Version
- [H2](https://www.h2database.com/html/main.html) - Database
- [Spring Boot](https://spring.io/) - Framework

---

## ✍️ Authors <a name = "authors"></a>

- [@GustavoAntunesRocha](https://github.com/GustavoAntunesRocha)

---
