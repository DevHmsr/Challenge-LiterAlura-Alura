# 📚 LiterAlura

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen)](https://spring.io/projects/spring-boot)
[![Build](https://img.shields.io/badge/build-passing-success)]()
[![License](https://img.shields.io/badge/license-MIT-blue)]()
[![API Gutendex](https://img.shields.io/badge/API-Gutendex-orange)](https://gutendex.com/)

**LiterAlura** é uma aplicação Java com Spring Boot que consome dados da [API Gutendex](https://gutendex.com/), permitindo buscar e registrar livros e autores em um banco de dados. O projeto oferece funcionalidades como listagem de livros, autores registrados, filtragem por idioma e verificação de autores vivos em um ano específico.

## 🚀 Funcionalidades

- 🔍 Buscar livro pelo título na API Gutendex
- 📝 Cadastrar automaticamente livro e autor no banco
- 📚 Listar todos os livros registrados
- ✍️ Listar todos os autores registrados
- 📆 Verificar autores vivos em determinado ano
- 🌍 Filtrar livros por idioma com contagem total

## 💻 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.5.4
- Spring Data JPA
- Hibernate
- PostegreSQL (ou outro banco relacional)
- Maven
- API externa: [Gutendex](https://gutendex.com/)

## 🧠 Estrutura do Projeto

### `Principal.java`

Classe principal com menu interativo via terminal. Aqui são chamadas todas as funcionalidades da aplicação, como:

- `buscarLivroPorTitulo()` – Realiza busca na API e salva os dados no banco.
- `listarLivrosRegistrados()` – Exibe todos os livros registrados.
- `listarAutoresRegistrados()` – Exibe todos os autores registrados.
- `listarAutoresVivos()` – Filtra autores vivos em um ano específico.
- `listarLivrosPorIdioma()` – Filtra livros por código de idioma e mostra a contagem.

### `Livro` e `Autor`

Entidades JPA que representam os modelos do domínio. Cada `Livro` está associado a um `Autor`.

### `LivroRepository` e `AutorRepository`

Repositórios JPA com consultas personalizadas:
- `findAutoresVivosNoAno(Integer ano)`
- `findByIdiomaIgnoreCase(String idioma)`
- `findByTituloAndAutor(String titulo, Autor autor)`

### `ConsumoAPI`

Classe utilitária responsável por fazer requisições HTTP à API Gutendex e retornar os dados JSON.

### `ConverteDados`

Classe de conversão que usa a biblioteca Gson para transformar JSON em objetos Java (DTOs).

### `DTOs`

- `Dados` – Representa o resultado da busca na API (lista de livros).
- `DadosLivro` – Representa os dados principais do livro retornado pela API.
- `DadosAutor` – Representa os dados do autor dentro de `DadosLivro`.

## 🛠️ Como Executar o Projeto

1. Clone este repositório:
   ```bash
   git clone https://github.com/DevHmsr/Challenge-LiterAlura-Alura.git
- Configure o banco de dados no application.properties:
```bash
spring.application.name=literalura

spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

```
- Lembre-se de sempre utilizar variáveis de ambiente caso vá subir esse projeto para algum repositório remoto!

- Rode o projeto em sua IDE ou via terminal

- Interaja com o menu no terminal!

📄 Exemplo de Uso
```
------------                                 
Escolha o número da opção desejada:
1 - Buscar livro pelo título
2 - Listar livros registrados
3 - Listar autores registrados
4 - Listar autores vivos em um determinado ano
5 - Listar livros em um determinado idioma
0 - Sair
Opção: 1
Digite o nome do livro
Frankenstein
------ LIVRO ------
Titulo: Frankenstein; Or, The Modern Prometheus
Autor: Shelley, Mary Wollstonecraft
Idioma: en
Número de downloads: 85232
-------------------
```

## 🎬 Demonstração da aplicação

### Menu de Aplicação

![Image](https://github.com/user-attachments/assets/7de1ea1f-f378-4f2d-aa72-265ed7c78919)

### Buscar livro pelo título

![Image](https://github.com/user-attachments/assets/8d842618-6ee9-4d8f-8c09-7d4740340345)

### Listar livros registrados

![Image](https://github.com/user-attachments/assets/a8eb99b7-2c92-4c1a-b8ce-197643172167)

### Listar autores registrados

![Image](https://github.com/user-attachments/assets/8ac0ed80-0128-41dc-9fc4-4682b6e91cae)

### Listar autores vivos em um determinado ano

![Image](https://github.com/user-attachments/assets/6cd7f815-4b68-4878-b5a7-b1e1d69b2c56)

### Listar livros em um determinado idioma

![Image](https://github.com/user-attachments/assets/6c235527-84da-415d-b07f-d6e1d33b1abf)

🧑‍🎓 Projeto Desenvolvido por [Dev Heitor Matos](https://github.com/DevHmsr) como um desafio prático da plataforma Alura.

## 📄 Licença

Este projeto está licenciado sob os termos da [MIT License](LICENSE).
