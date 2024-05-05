package com.github.ericknathan.books.dtos.author;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateAuthorDTO(
        @Size(min = 2, max = 100)
        String name,
        @Past
        LocalDate birthDate,

        @Size(min = 10, max = 500)
        String biography
) {
}
