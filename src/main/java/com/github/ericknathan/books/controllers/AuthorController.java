package com.github.ericknathan.books.controllers;

import com.github.ericknathan.books.dtos.author.AuthorDetailsDTO;
import com.github.ericknathan.books.dtos.author.CreateAuthorDTO;
import com.github.ericknathan.books.dtos.author.UpdateAuthorDTO;
import com.github.ericknathan.books.dtos.book.BookDetailsDTO;
import com.github.ericknathan.books.models.AuthorModel;
import com.github.ericknathan.books.repositories.AuthorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("authors")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("{id}")
    public ResponseEntity<AuthorDetailsDTO> getAuthorById(@PathVariable Long id) {
        var author = authorRepository.findById(id);

        if (author.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new AuthorDetailsDTO(author.get()));
    }

    @GetMapping
    public ResponseEntity<List<AuthorDetailsDTO>> getAuthors(Pageable pageable) {
        var authors = authorRepository.findAll(pageable)
                .stream().map(AuthorDetailsDTO::new).toList();

        return ResponseEntity.ok(authors);
    }

    @GetMapping("{id}/books")
    public ResponseEntity<List<BookDetailsDTO>> getBooksByAuthor(@PathVariable Long id) {
        var author = authorRepository.findById(id);

        if (author.isEmpty()) return ResponseEntity.notFound().build();

        var books = author.get().getBooks().stream().map(BookDetailsDTO::new).toList();

        return ResponseEntity.ok(books);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<AuthorDetailsDTO> createAuthor(@RequestBody @Valid CreateAuthorDTO authorDTO, UriComponentsBuilder uriBuilder) {
        var author = new AuthorModel(authorDTO);
        authorRepository.save(author);

        var uri = uriBuilder.path("authors/{id}").buildAndExpand(author.getId()).toUri();
        return ResponseEntity.created(uri).body(new AuthorDetailsDTO(author));
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<AuthorDetailsDTO> updateAuthor(@PathVariable Long id, @RequestBody @Valid UpdateAuthorDTO authorDTO) {
        var author = authorRepository.findById(id);

        if (author.isEmpty()) return ResponseEntity.notFound().build();

        var updatedAuthor = author.get();
        updatedAuthor.update(authorDTO);
        authorRepository.save(updatedAuthor);

        return ResponseEntity.ok(new AuthorDetailsDTO(updatedAuthor));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        var author = authorRepository.findById(id);

        if (author.isEmpty()) return ResponseEntity.notFound().build();

        author.get().removeBooks();
        authorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
