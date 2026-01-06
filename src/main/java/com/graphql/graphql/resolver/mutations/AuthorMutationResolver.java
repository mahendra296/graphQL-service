package com.graphql.graphql.resolver.mutations;

import com.graphql.dto.AuthorInput;
import com.graphql.models.Author;
import com.graphql.services.AuthorService;
// import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorMutationResolver { // implements GraphQLMutationResolver {

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
