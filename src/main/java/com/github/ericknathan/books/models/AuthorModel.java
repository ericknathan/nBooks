package com.github.ericknathan.books.models;

import com.github.ericknathan.books.dtos.author.CreateAuthorDTO;
import com.github.ericknathan.books.dtos.author.UpdateAuthorDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name="TB_AUTOR")
public class AuthorModel {
    @Id
    @GeneratedValue
    @Column(name="id_autor")
    private Long id;

    @Column(name="nome", nullable = false, length = 100)
    private String name;

    @Column(name="data_nascimento")
    private LocalDate birthDate;

    @Column(name="biografia", length = 500)
    private String biography;

    @ManyToMany(mappedBy = "authors")
    private Set<BookModel> books = new HashSet<>();

    public AuthorModel(CreateAuthorDTO authorDTO) {
        this.name = authorDTO.name();
        if(authorDTO.birthDate() != null) this.birthDate = authorDTO.birthDate();
        this.biography = authorDTO.biography();
    }

    public void update(UpdateAuthorDTO authorDTO) {
        if(authorDTO.name() != null) this.name = authorDTO.name();
        if(authorDTO.birthDate() != null) this.birthDate = authorDTO.birthDate();
        if(authorDTO.biography() != null) this.biography = authorDTO.biography();
    }

    public void removeBooks() {
        for(BookModel book : new ArrayList<>(books)) {
            book.getAuthors().remove(this);
            this.books.remove(book);
        }
    }
}
