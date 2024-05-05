package com.github.ericknathan.books.enums;

public enum BookGenre {

    ROMANCE("Romance"),
    FANTASY("Fantasia"),
    SCIENCE_FICTION("Ficção Científica"),
    TERROR("Terror"),
    SUSPENSE("Suspense"),
    DRAMA("Drama"),
    ADVENTURE("Aventura"),
    MYSTERY("Mistério"),
    THRILLER("Thriller"),
    HISTORICAL_FICTION("Ficção Histórica"),
    BIOGRAPHY("Biografia");

    private String genre;

    BookGenre(String genre) {
        this.genre = genre;
    }
}
