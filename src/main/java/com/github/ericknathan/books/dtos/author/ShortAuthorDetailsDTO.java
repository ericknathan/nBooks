package com.github.ericknathan.books.dtos.author;

import com.github.ericknathan.books.models.AuthorModel;

public record ShortAuthorDetailsDTO(
        Long id,
        String name
) {
    public ShortAuthorDetailsDTO(AuthorModel author) {
        this(author.getId(), author.getName());
    }
}
