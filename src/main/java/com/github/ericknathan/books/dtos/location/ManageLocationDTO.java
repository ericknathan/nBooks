package com.github.ericknathan.books.dtos.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ManageLocationDTO(
        @NotBlank @Size(min = 2, max = 100)
        String street,
        @NotBlank @Size(min = 1, max = 10)
        String number,
        @NotBlank @Size(min = 2, max = 100)
        String city,
        @NotBlank @Size(min = 2, max = 100)
        String state,
        @NotBlank @Size(min = 8, max = 8)
        String zipCode
) {
}
