package com.graphql.repository;

import com.graphql.models.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorId(String authorId);
}
