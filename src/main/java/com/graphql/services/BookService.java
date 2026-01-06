package com.graphql.services;

import com.graphql.models.Book;
import com.graphql.repository.BookRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        List<Book> allBook = bookRepository.findAll();
        return allBook;
    }

    public Book getBookById(Long bookID) {
        Optional<Book> book = bookRepository.findById(bookID);
        return book.orElse(null);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Book book) {
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> getBooksByAuthorId(String authorId) {
        return bookRepository.findByAuthorId(authorId);
    }
}
