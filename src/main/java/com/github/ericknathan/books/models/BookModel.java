package com.github.ericknathan.books.models;

import com.github.ericknathan.books.dtos.book.CreateBookDTO;
import com.github.ericknathan.books.dtos.book.UpdateBookDTO;
import com.github.ericknathan.books.enums.BookGenre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="TB_LIVRO")
public class BookModel {
    @Id
    @GeneratedValue
    @Column(name="id_livro")
    private Long id;

    @Column(name="titulo", nullable = false, length = 100)
    private String title;

    @Column(name="publicado_em", nullable = false)
    private LocalDate publishedAt;

    @Column(name="resumo", nullable = false, length = 500)
    private String summary;

    @Column(name="paginas", nullable = false)
    private Integer pages;

    @Column(name="idioma", nullable = false, length = 50)
    private String language;

    @Column(name="genero", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private BookGenre genre;

    @ManyToOne
    @JoinColumn(name="id_editora", nullable = false)
    private CompanyModel company;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "TB_LIVRO_AUTOR",
            joinColumns = @JoinColumn(name = "id_livro"),
            inverseJoinColumns = @JoinColumn(name = "id_autor"))
    private Set<AuthorModel> authors = new HashSet<>();

    public BookModel(CreateBookDTO bookDTO, CompanyModel company, List<AuthorModel> authors) {
        this.title = bookDTO.title();
        this.publishedAt = bookDTO.publishedAt();
        this.summary = bookDTO.summary();
        this.pages = bookDTO.pages();
        this.language = bookDTO.language();
        this.genre = bookDTO.genre();
        this.company = company;
        this.authors.addAll(authors);
    }

    public void update(UpdateBookDTO bookDTO) {
        if(bookDTO.title() != null) this.title = bookDTO.title();
        if(bookDTO.publishedAt() != null) this.publishedAt = bookDTO.publishedAt();
        if(bookDTO.summary() != null) this.summary = bookDTO.summary();
        if(bookDTO.pages() != null) this.pages = bookDTO.pages();
        if(bookDTO.language() != null) this.language = bookDTO.language();
        if(bookDTO.genre() != null) this.genre = bookDTO.genre();
    }
}
