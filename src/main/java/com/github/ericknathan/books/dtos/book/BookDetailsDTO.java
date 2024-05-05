package com.github.ericknathan.books.dtos.book;

import com.github.ericknathan.books.dtos.company.CompanyDetailsDTO;
import com.github.ericknathan.books.enums.BookGenre;
import com.github.ericknathan.books.dtos.author.ShortAuthorDetailsDTO;
import com.github.ericknathan.books.models.BookModel;

import java.util.List;

public record BookDetailsDTO(
        Long id,
        String title,
        String publishedAt,
        String summary,
        int pages,
        String language,
        BookGenre genre,
        List<ShortAuthorDetailsDTO> authors,
        Long companyId
) {
    public BookDetailsDTO(BookModel book) {
        this(
                book.getId(), book.getTitle(), book.getPublishedAt().toString(), book.getSummary(), book.getPages(),
                book.getLanguage(), book.getGenre(), book.getAuthors().stream().map(ShortAuthorDetailsDTO::new).toList(),
                book.getCompany().getId()
        );
    }
}
