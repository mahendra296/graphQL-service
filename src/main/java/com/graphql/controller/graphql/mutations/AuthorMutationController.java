package com.graphql.controller.graphql.mutations;

import com.graphql.dto.AuthorInput;
import com.graphql.models.Author;
import com.graphql.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthorMutationController {

    private final AuthorService authorService;

    @MutationMapping
    public Author createAuthor(@Argument AuthorInput input) {
        Author author = new Author();
        author.setName(input.name());
        author.setEmail(input.email());
        return authorService.saveAuthor(author);
    }

    @MutationMapping
    public Boolean deleteAuthor(@Argument Long id) {
        authorService.delete(id);
        return true;
    }
}
