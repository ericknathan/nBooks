package com.github.ericknathan.books.controllers;


import com.github.ericknathan.books.dtos.book.BookDetailsDTO;
import com.github.ericknathan.books.dtos.book.CreateBookDTO;
import com.github.ericknathan.books.dtos.book.UpdateBookDTO;
import com.github.ericknathan.books.models.BookModel;
import com.github.ericknathan.books.models.CompanyModel;
import com.github.ericknathan.books.repositories.AuthorRepository;
import com.github.ericknathan.books.repositories.BookRepository;
import com.github.ericknathan.books.repositories.CompanyRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    public ResponseEntity<List<BookDetailsDTO>> getBooks(Pageable pageable) {
        var books = bookRepository.findAll(pageable)
                .stream().map(BookDetailsDTO::new).toList();

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
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        var book = bookRepository.findById(id);

        if (book.isEmpty()) return ResponseEntity.notFound().build();

        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
