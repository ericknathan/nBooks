package com.github.ericknathan.books.repositories;

import com.github.ericknathan.books.models.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyModel, Long> {
}
