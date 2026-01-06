package com.graphql.controller.graphql.queries;

import com.graphql.models.Author;
import com.graphql.models.Book;
import com.graphql.services.AuthorService;
import com.graphql.services.BookService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AuthorQueryController {

    private final AuthorService authorService;
    private final BookService bookService;

    @QueryMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthor();
    }

    @QueryMapping
    public Author getAuthorById(@Argument Long authorId) {
        return authorService.getAuthorById(authorId);
    }

    @SchemaMapping(typeName = "Author", field = "books")
    public List<Book> getBooks(Author author) {
        if (author.getId() == null) {
            return Collections.emptyList();
        }
        return bookService.getBooksByAuthorId(author.getId().toString());
    }
}
