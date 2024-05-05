package com.github.ericknathan.books.dtos.company;

import com.github.ericknathan.books.dtos.location.ManageLocationDTO;
import jakarta.validation.constraints.Size;

public record UpdateCompanyDTO(
        @Size(min = 2, max = 100)
        String name,
        @Size(min = 14, max = 14)
        String cnpj,
        ManageLocationDTO location
) {
}
