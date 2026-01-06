package com.graphql.controller.graphql.queries;

import com.graphql.models.Author;
import com.graphql.models.Book;
import com.graphql.services.AuthorService;
import com.graphql.services.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BooksQueryController {

    private final BookService bookService;
    private final AuthorService authorService;

    @QueryMapping
    public List<Book> getAllBooks() {
        List<Book> allBook = bookService.getAllBooks();
        return allBook;
    }

    @QueryMapping
    public Book getBookById(@Argument Integer bookID) {
        Book book = bookService.getBookById(Long.valueOf(bookID));
        return book;
    }

    @SchemaMapping(typeName = "Book", field = "author")
    public Author getAuthor(Book book) {
        if (book.getAuthorId() == null) {
            return null;
        }
        return authorService.getAuthorById(Long.parseLong(book.getAuthorId()));
    }
}
