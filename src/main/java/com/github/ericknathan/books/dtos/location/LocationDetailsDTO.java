package com.github.ericknathan.books.dtos.location;

import com.github.ericknathan.books.models.LocationModel;

public record LocationDetailsDTO(
        Long id,
        String street,
        String number,
        String city,
        String state,
        String zipCode
) {
    public LocationDetailsDTO(LocationModel location) {
        this(location.getId(), location.getStreet(), location.getNumber(), location.getCity(), location.getState(), location.getZipCode());
    }
}
