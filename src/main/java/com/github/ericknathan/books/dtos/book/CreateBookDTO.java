package com.github.ericknathan.books.dtos.book;

import com.github.ericknathan.books.enums.BookGenre;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record CreateBookDTO(
        @NotBlank
        Long companyId,
        @NotBlank @Size(min = 2, max = 100)
        String title,
        @NotBlank @Past
        LocalDate publishedAt,
        @NotBlank @Size(min = 10, max = 500)
        String summary,
        @NotBlank @Size(min = 1)
        Integer pages,
        @NotBlank @Size(min = 2, max = 50)
        String language,
        @NotBlank @Size(min = 2, max = 50)
        BookGenre genre,
        @NotBlank
        List<Long> authors
) {
}
