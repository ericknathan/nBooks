package com.github.ericknathan.books.dtos.book;

import com.github.ericknathan.books.enums.BookGenre;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateBookDTO(
        @Size(min = 2, max = 100)
        String title,
        @Past
        LocalDate publishedAt,
        @Size(min = 10, max = 500)
        String summary,
        @Size(min = 1)
        Integer pages,
        @Size(min = 2, max = 50)
        String language,
        @Size(min = 2, max = 50)
        BookGenre genre
) {
}
