package com.github.ericknathan.books.repositories;

import com.github.ericknathan.books.models.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookModel, Long> {
}
