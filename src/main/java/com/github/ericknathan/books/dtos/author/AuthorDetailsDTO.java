package com.github.ericknathan.books.dtos.author;

import com.github.ericknathan.books.models.AuthorModel;

import java.time.LocalDate;

public record AuthorDetailsDTO(
        Long id,
        String name,
        LocalDate birthDate,
        String biography
) {
    public AuthorDetailsDTO(AuthorModel author) {
        this(author.getId(), author.getName(), author.getBirthDate(), author.getBiography());
    }
}
