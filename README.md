# GraphQL Service with Spring Boot

A complete example of GraphQL implementation using **Spring for GraphQL** and **GraphQL Java Kickstart** with Spring Boot 3.x.

## Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [GraphQL Schema](#graphql-schema)
- [Implementation Approaches](#implementation-approaches)
  - [Spring for GraphQL](#spring-for-graphql)
  - [GraphQL Java Kickstart](#graphql-java-kickstart)
- [API Examples](#api-examples)
- [Configuration](#configuration)

## Overview

This project demonstrates how to build a GraphQL API using Spring Boot with two different approaches:
1. **Spring for GraphQL** - The official Spring integration for GraphQL (used for endpoint handling)
2. **GraphQL Java Kickstart** - Schema-first approach with resolver classes (used for business logic organization)

## Tech Stack

- Java 21
- Spring Boot 3.4.1
- Spring for GraphQL
- GraphQL Java Kickstart Tools
- Spring Data JPA
- MySQL Database
- Lombok

## Project Structure

```
src/main/java/com/graphql/
├── controller/
│   └── graphql/
│       ├── queries/              # Spring GraphQL Query Controllers
│       │   ├── AuthorQueryController.java
│       │   └── BooksQueryController.java
│       └── mutations/            # Spring GraphQL Mutation Controllers
│           ├── AuthorMutationController.java
│           └── BookMutationController.java
├── graphql/
│   └── resolver/                 # Kickstart Resolvers (Business Logic)
│       ├── queries/
│       │   └── AuthorQueryResolver.java
│       └── mutations/
│           └── AuthorMutationResolver.java
├── dto/                          # Data Transfer Objects
│   ├── AuthorInput.java
│   └── BookInput.java
├── models/                       # JPA Entities
│   ├── Author.java
│   └── Book.java
├── repository/                   # Spring Data Repositories
│   ├── AuthorRepository.java
│   └── BookRepository.java
├── services/                     # Service Layer
│   ├── AuthorService.java
│   └── BookService.java
└── GraphQLApplication.java

src/main/resources/
├── graphql/                      # GraphQL Schema Files
│   ├── Base.graphqls            # Base Query & Mutation types
│   ├── Authors.graphqls         # Author schema
│   └── Books.graphqls           # Book schema
└── application.properties
```

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.8+
- MySQL 8.0+

### Database Setup

```sql
CREATE DATABASE testdb;
```

### Running the Application

```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

### Access Points

- **GraphQL Endpoint:** http://localhost:8081/graphql
- **Apollo Sandbox:** http://localhost:8081/apollo.html
- **GraphQL Playground:** http://localhost:8081/playground.html
- **Altair Client:** http://localhost:8081/altair.html

## GraphQL Schema

### Base Schema (Base.graphqls)

```graphql
type Query {}
type Mutation {}
```

### Author Schema (Authors.graphqls)

```graphql
extend type Query {
    getAllAuthors: [Author]
    getAuthorById(authorId: ID!): Author
}

extend type Mutation {
    createAuthor(input: AuthorInput!): Author
    deleteAuthor(id: ID!): Boolean
}

input AuthorInput {
    name: String!
    email: String
}
```

### Book Schema (Books.graphqls)

```graphql
extend type Query {
    getAllBooks: [Book]
    getBookById(bookID: Int): Book
}

extend type Mutation {
    saveBook(book: BookInput!): Book
    createBook(input: BookInput!): Book
    updateBook(id: ID!, input: BookInput!): Book!
    deleteBook(id: ID!): Boolean!
}

type Author {
    id: ID!
    name: String!
    email: String
    books: [Book!]!
}

type Book {
    id: ID
    name: String
    price: String
    author: Author!
    publisherDate: String
}

input BookInput {
    title: String!
    price: String!
    authorId: ID!
    publishedDate: String
}
```

## Implementation Approaches

### Spring for GraphQL

Spring for GraphQL uses annotation-based controllers to handle GraphQL operations. This is the recommended approach for Spring Boot 3.x.

#### Query Controller Example

```java
@Controller
@RequiredArgsConstructor
public class BooksQueryController {

    private final BookService bookService;

    @QueryMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @QueryMapping
    public Book getBookById(@Argument Integer bookID) {
        return bookService.getBookById(Long.valueOf(bookID));
    }
}
```

#### Mutation Controller Example

```java
@Controller
@RequiredArgsConstructor
public class BookMutationController {

    private final BookService bookService;

    @MutationMapping
    public Book createBook(@Argument BookInput input) {
        Book book = new Book();
        book.setName(input.name());
        book.setAuthorId(input.authorId());
        return bookService.saveBook(book);
    }

    @MutationMapping
    public Boolean deleteBook(@Argument Long id) {
        bookService.delete(id);
        return true;
    }
}
```

#### Key Annotations

| Annotation | Description |
|------------|-------------|
| `@Controller` | Marks class as Spring MVC controller |
| `@QueryMapping` | Maps method to GraphQL Query field |
| `@MutationMapping` | Maps method to GraphQL Mutation field |
| `@Argument` | Binds GraphQL argument to method parameter |
| `@SchemaMapping` | Maps method to GraphQL type field resolver |

### GraphQL Java Kickstart

Kickstart uses resolver interfaces for organizing GraphQL operations. While Spring Boot 3.x doesn't fully support Kickstart's web integration, you can still use Kickstart's resolver pattern for organizing business logic.

#### Query Resolver Example

```java
@Component
@RequiredArgsConstructor
public class AuthorQueryResolver implements GraphQLQueryResolver {

    private final AuthorService authorService;

    public List<Author> getAllAuthors() {
        return authorService.getAllAuthor();
    }

    public Author getAuthorById(Long authorId) {
        return authorService.getAuthorById(authorId);
    }
}
```

#### Mutation Resolver Example

```java
@Component
@RequiredArgsConstructor
public class AuthorMutationResolver implements GraphQLMutationResolver {

    private final AuthorService authorService;

    public Author createAuthor(AuthorInput input) {
        Author author = new Author();
        author.setName(input.name());
        author.setEmail(input.email());
        return authorService.saveAuthor(author);
    }

    public Boolean deleteAuthor(Long id) {
        authorService.delete(id);
        return true;
    }
}
```

#### Kickstart Interfaces

| Interface | Description |
|-----------|-------------|
| `GraphQLQueryResolver` | Implement for Query operations |
| `GraphQLMutationResolver` | Implement for Mutation operations |
| `GraphQLSubscriptionResolver` | Implement for Subscription operations |
| `GraphQLResolver<T>` | Implement for type field resolvers |

### Combining Both Approaches

In this project, we use both approaches together:
- **Spring GraphQL Controllers** handle the `/graphql` endpoint
- **Kickstart Resolvers** organize business logic and can be called from controllers

```java
@Controller
@RequiredArgsConstructor
public class AuthorQueryController {

    private final AuthorService authorService;

    @QueryMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthor();
    }
}
```

## API Examples

### Queries

#### Get All Authors
```graphql
query {
  getAllAuthors {
    id
    name
    email
  }
}
```

#### Get Author by ID
```graphql
query {
  getAuthorById(authorId: 1) {
    id
    name
    email
  }
}
```

#### Get All Books
```graphql
query {
  getAllBooks {
    id
    name
    price
    publisherDate
  }
}
```

### Mutations

#### Create Author
```graphql
mutation {
  createAuthor(input: {
    name: "John Doe"
    email: "john@example.com"
  }) {
    id
    name
    email
  }
}
```

#### Create Book
```graphql
mutation {
  createBook(input: {
    title: "GraphQL Guide"
    price: "123"
    authorId: "1"
    publishedDate: "2024-01-01"
  }) {
    id
    name
    price
  }
}
```

#### Delete Author
```graphql
mutation {
  deleteAuthor(id: 1)
}
```

## Configuration

### application.properties

```properties
# Server
server.port=8081

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/testdb
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# GraphQL
spring.graphql.graphiql.enabled=false
spring.graphql.path=/graphql
spring.graphql.schema.introspection.enabled=true
spring.graphql.schema.printer.enabled=true
spring.graphql.schema.locations=classpath:graphql/
```

### GraphQL Playgrounds

This project includes three GraphQL client interfaces in `src/main/resources/static/`:

| Client | URL | Description |
|--------|-----|-------------|
| Apollo Sandbox | `/apollo.html` | Modern Apollo Studio embedded sandbox |
| GraphQL Playground | `/playground.html` | Classic GraphQL Playground interface |
| Altair | `/altair.html` | Feature-rich GraphQL client |

### Maven Dependencies (pom.xml)

```xml
<!-- Spring for GraphQL -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-graphql</artifactId>
</dependency>

<!-- GraphQL Java Kickstart Tools -->
<dependency>
    <groupId>com.graphql-java-kickstart</groupId>
    <artifactId>graphql-java-tools</artifactId>
    <version>13.0.0</version>
</dependency>
```

## Comparison: Spring GraphQL vs Kickstart

| Feature | Spring for GraphQL | GraphQL Java Kickstart |
|---------|-------------------|------------------------|
| Spring Boot 3.x Support | Full support | Limited (tools only) |
| Configuration | Auto-configured | Manual setup required |
| Annotations | `@QueryMapping`, `@MutationMapping` | Interface-based |
| Schema Location | `src/main/resources/graphql/` | Configurable |
| Testing | `@GraphQlTest` support | Custom test setup |
| Recommended For | New Spring Boot 3.x projects | Legacy or specific patterns |

## License

This project is open source and available under the MIT License.
