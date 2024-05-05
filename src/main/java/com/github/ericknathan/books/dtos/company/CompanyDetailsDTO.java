package com.github.ericknathan.books.dtos.company;

import com.github.ericknathan.books.dtos.location.LocationDetailsDTO;
import com.github.ericknathan.books.models.CompanyModel;

public record CompanyDetailsDTO(
        Long id,
        String name,
        String cnpj,
        LocationDetailsDTO location
) {
    public CompanyDetailsDTO(CompanyModel company) {
        this(company.getId(), company.getName(), company.getCnpj(), new LocationDetailsDTO(company.getLocation()));
    }
}
