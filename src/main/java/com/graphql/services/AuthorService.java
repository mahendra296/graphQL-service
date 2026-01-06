package com.graphql.services;

import com.graphql.models.Author;
import com.graphql.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long authorId) {
        Optional<Author> author = authorRepository.findById(authorId);
        return author.orElse(null);
    }

    public Author saveAuthor(Author book) {
        return authorRepository.save(book);
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
