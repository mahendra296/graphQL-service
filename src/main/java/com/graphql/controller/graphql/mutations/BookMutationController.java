package com.graphql.controller.graphql.mutations;

import com.graphql.dto.BookInput;
import com.graphql.models.Book;
import com.graphql.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BookMutationController {

    private final BookService bookService;

    @MutationMapping
    public Book saveBook(@Argument BookInput book) {
        Book newBook = new Book();
        newBook.setTitle(book.title());
        newBook.setPrice(book.price());
        newBook.setAuthorId(book.authorId());
        newBook.setPublishedDate(book.publishedDate());
        return bookService.saveBook(newBook);
    }

    @MutationMapping
    public Book createBook(@Argument BookInput input) {
        Book book = new Book();
        book.setTitle(input.title());
        book.setAuthorId(input.authorId());
        book.setPrice(input.price());
        book.setPublishedDate(input.publishedDate());

        return bookService.saveBook(book);
    }

    @MutationMapping
    public Book updateBook(@Argument Long id, @Argument BookInput input) {
        Book book = bookService.getBookById(id);
        book.setTitle(input.title());
        book.setPrice(input.price());
        book.setAuthorId(input.authorId());
        book.setPublishedDate(input.publishedDate());

        return bookService.update(book);
    }

    @MutationMapping
    public Boolean deleteBook(@Argument Long id) {
        bookService.delete(id);
        return true;
    }
}
