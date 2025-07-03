# 📚 Literalura

Este projeto é uma aplicação de console em Java, desenvolvida com Spring Boot, que permite aos usuários buscar livros na API do Gutendex (que oferece acesso a livros de domínio público) e persistir os dados desses livros e seus autores em um banco de dados PostgreSQL. O objetivo é criar um catálogo local de livros clássicos.

---

## 🚀 Funcionalidades

A aplicação Literalura oferece as seguintes funcionalidades através de um menu interativo no console:

- **Buscar Livro por Título**: Consome a API do Gutendex para encontrar livros e salva os dados do livro, autor e idiomas no banco de dados.
- **Listar Todos os Livros**: Exibe todos os livros que foram salvos no banco de dados.
- **Listar Autores Registrados**: Exibe todos os autores cujos livros foram salvos no banco de dados.
- **Listar Autores Vivos em Determinado Ano**: Filtra e exibe autores que estavam vivos em um ano específico.
- **Listar Livros por Idioma**: Filtra e exibe livros de acordo com um idioma selecionado (ex: pt, en, es).

---

## 🛠️ Tecnologias Utilizadas

- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- Hibernate
- PostgreSQL
- Jackson
- Java.net.http.HttpClient

---

## ⚙️ Configuração e Instalação

### ✅ Pré-requisitos

- JDK 17 ou superior
- Maven
- PostgreSQL rodando localmente
- IDE (IntelliJ)

---

## 🧪 Como executar o projeto

### 1. Clonar o Repositório

```bash
git clone <URL_DO_SEU_REPOSITORIO>
cd literalura
```

---

### 2. Configurar o Banco de Dados

Crie um banco PostgreSQL para a aplicação:

```sql
CREATE DATABASE literalura_db;
CREATE USER literalura_user WITH PASSWORD 'sua_senha';
GRANT ALL PRIVILEGES ON DATABASE literalura_db TO literalura_user;
```

---

### 3. Configurar o `application.properties`

📁 Local do arquivo: `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5438/literalura_db
spring.datasource.username=seu_user
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

⚠️ Substitua `seu_user` e `sua_senha` pelas suas credenciais.

---

### 4. Executar a Aplicação

#### ✅ Usando Maven no terminal:

```bash
mvn spring-boot:run
```

#### ✅ Ou via IDE:

Execute a classe `LiteraluraApplication`, que contém o método `main`.

---

## 👨‍💻 Autor

Feito por **Petterson Oliveira**  
Aluno do programa **Oracle Next Education - ONE** e **Tecnólogo da faculdade Celso Lisboa** 🚀  
