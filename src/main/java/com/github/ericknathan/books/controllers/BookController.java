package com.github.ericknathan.books.controllers;


import com.github.ericknathan.books.dtos.book.BookDetailsDTO;
import com.github.ericknathan.books.dtos.book.CountByCompanyDTO;
import com.github.ericknathan.books.dtos.book.CreateBookDTO;
import com.github.ericknathan.books.dtos.book.UpdateBookDTO;
import com.github.ericknathan.books.models.BookModel;
import com.github.ericknathan.books.models.CompanyModel;
import com.github.ericknathan.books.repositories.AuthorRepository;
import com.github.ericknathan.books.repositories.BookRepository;
import com.github.ericknathan.books.repositories.CompanyRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("{id}")
    public ResponseEntity<BookDetailsDTO> getBookById(@PathVariable Long id) {
        var book = bookRepository.findById(id);

        if (book.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new BookDetailsDTO(book.get()));
    }

    @GetMapping
    public ResponseEntity<Page<BookDetailsDTO>> getBooks(Pageable pageable) {
        var books = bookRepository.findAll(pageable).map(BookDetailsDTO::new);

        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<BookDetailsDTO> createBook(@RequestBody @Valid CreateBookDTO bookDTO, UriComponentsBuilder uriBuilder) {
        var company = companyRepository.findById(bookDTO.companyId());

        if (company.isEmpty()) return ResponseEntity.notFound().build();

        var authors = authorRepository.findAllById(bookDTO.authors());
        if (authors.size() != bookDTO.authors().size()) return ResponseEntity.badRequest().build();

        var book = new BookModel(bookDTO, company.get(), authors);
        bookRepository.save(book);

        var uri = uriBuilder.path("books/{id}").buildAndExpand(book.getId()).toUri();
        return ResponseEntity.created(uri).body(new BookDetailsDTO(book));
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDetailsDTO> updateBook(@PathVariable Long id, @RequestBody @Valid UpdateBookDTO bookDTO) {
        var book = bookRepository.findById(id);

        if (book.isEmpty()) return ResponseEntity.notFound().build();

        var updatedBook = book.get();
        updatedBook.update(bookDTO);
        bookRepository.save(updatedBook);

        return ResponseEntity.ok(new BookDetailsDTO(updatedBook));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        var book = bookRepository.findById(id);

        if (book.isEmpty()) return ResponseEntity.notFound().build();

        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("search/by-title")
    public ResponseEntity<Page<BookDetailsDTO>> getBooksByTitle(@RequestParam String title, Pageable pageable) {
        var books = bookRepository.findByTitle(title, pageable).map(BookDetailsDTO::new);

        return ResponseEntity.ok(books);
    }

    @GetMapping("search/by-date")
    public ResponseEntity<Page<BookDetailsDTO>> getBooksByDate(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate, Pageable pageable) {
        var books = bookRepository.findByPublishedAtBetween(startDate, endDate, pageable).map(BookDetailsDTO::new);

        return ResponseEntity.ok(books);
    }

    @GetMapping("search/by-company-and-title")
    public ResponseEntity<Page<BookDetailsDTO>> getBooksByCompany(@RequestParam Long companyId, @RequestParam String title, Pageable pageable) {
        var books = bookRepository.findByCompanyIdAndTitle(companyId, title, pageable).map(BookDetailsDTO::new);

        return ResponseEntity.ok(books);
    }

    @GetMapping("count/by-company")
    public ResponseEntity<CountByCompanyDTO> countBooksByCompany(@RequestParam Long companyId) {
        var count = bookRepository.countBooksByCompany(companyId);

        return ResponseEntity.ok(new CountByCompanyDTO(count, companyId));
    }


}
