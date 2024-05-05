package com.github.ericknathan.books.dtos.company;

import com.github.ericknathan.books.dtos.location.ManageLocationDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCompanyDTO(
        @NotBlank @Size(min = 2, max = 100)
        String name,
        @NotBlank @Size(min = 14, max = 14)
        String cnpj,
        @NotBlank
        ManageLocationDTO location
) {
}
