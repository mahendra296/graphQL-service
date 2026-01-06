package com.graphql.controller;

import com.graphql.models.Book;
import com.graphql.services.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/getAllBook")
    public List<Book> getAllBook() {
        List<Book> allBooks = bookService.getAllBooks();
        return allBooks;
    }

    @PostMapping("/saveBook")
    public void saveBook(@RequestBody Book book) {
        bookService.saveBook(book);
    }
}
