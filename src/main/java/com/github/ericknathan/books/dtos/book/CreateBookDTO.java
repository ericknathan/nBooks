package com.github.ericknathan.books.dtos.book;

import com.github.ericknathan.books.enums.BookGenre;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record CreateBookDTO(
        @NotNull
        Long companyId,
        @NotBlank @Size(min = 2, max = 100)
        String title,
        @NotNull @Past
        LocalDate publishedAt,
        @NotBlank @Size(min = 10, max = 500)
        String summary,
        @NotNull @Positive
        Integer pages,
        @NotBlank @Size(min = 2, max = 50)
        String language,
        @NotNull
        BookGenre genre,
        @NotEmpty
        List<Long> authors
) {
}
