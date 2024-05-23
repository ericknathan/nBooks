package com.github.ericknathan.books.repositories;

import com.github.ericknathan.books.models.BookModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface BookRepository extends JpaRepository<BookModel, Long> {
    // Pesquisar por parte de uma String, sem considerar maiúsculas minúsculas
    @Query("from BookModel b where lower(b.title) like lower(concat('%', :title, '%'))")
    Page<BookModel> findByTitle(String title, Pageable pageable);

    // Pesquisar por intervalo de datas
    @Query("from BookModel b where b.publishedAt between :startDate and :endDate")
    Page<BookModel> findByPublishedAtBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    // Pesquisar utilizando dois parâmetros
    @Query("from BookModel b where b.company.id = :companyId and lower(b.title) like lower(concat('%', :title, '%'))")
    Page<BookModel> findByCompanyIdAndTitle(Long companyId, String title, Pageable pageable);

    // Pesquisar navegando entre os atributos de relacionamentos
    // Pesquisar por qualquer parâmetro
    @Query("select count(b) from BookModel b where b.company.id = :companyId")
    Long countBooksByCompany(Long companyId);
}
