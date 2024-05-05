package com.github.ericknathan.books.repositories;

import com.github.ericknathan.books.models.AuthorModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<AuthorModel, Long> {
}
