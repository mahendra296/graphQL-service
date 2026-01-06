package com.graphql.graphql.resolver.queries;

import com.graphql.models.Author;
import com.graphql.services.AuthorService;
// import graphql.kickstart.tools.GraphQLQueryResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/*
for this Need to add below dependency
<dependency>
    <groupId>com.graphql-java-kickstart</groupId>
    <artifactId>graphql-java-tools</artifactId>
    <version>13.0.0</version>
</dependency>
*/
@Component
@RequiredArgsConstructor
public class AuthorQueryResolver { // implements GraphQLQueryResolver {

    private final AuthorService authorService;

    public List<Author> getAllAuthors() {
        return authorService.getAllAuthor();
    }

    public Author getAuthorById(Long authorId) {
        return authorService.getAuthorById(authorId);
    }
}
