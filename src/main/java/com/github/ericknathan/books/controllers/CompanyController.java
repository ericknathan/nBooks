package com.github.ericknathan.books.controllers;

import com.github.ericknathan.books.dtos.book.BookDetailsDTO;
import com.github.ericknathan.books.dtos.company.CompanyDetailsDTO;
import com.github.ericknathan.books.dtos.company.CreateCompanyDTO;
import com.github.ericknathan.books.dtos.company.UpdateCompanyDTO;
import com.github.ericknathan.books.models.CompanyModel;
import com.github.ericknathan.books.repositories.CompanyRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("companies")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("{id}")
    public ResponseEntity<CompanyDetailsDTO> getCompanyById(@PathVariable Long id) {
        var company = companyRepository.findById(id);

        if (company.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(new CompanyDetailsDTO(company.get()));
    }

    @GetMapping
    public ResponseEntity<List<CompanyDetailsDTO>> getCompanies(Pageable pageable) {
        var companies = companyRepository.findAll(pageable)
                .stream().map(CompanyDetailsDTO::new).toList();

        return ResponseEntity.ok(companies);
    }

    @PostMapping
    public ResponseEntity<CompanyDetailsDTO> createCompany(@RequestBody @Valid CreateCompanyDTO companyDTO, UriComponentsBuilder uriBuilder) {
        var company = new CompanyModel(companyDTO);
        companyRepository.save(company);

        var uri = uriBuilder.path("authors/{id}").buildAndExpand(company.getId()).toUri();
        return ResponseEntity.created(uri).body(new CompanyDetailsDTO(company));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @RequestBody @Valid UpdateCompanyDTO companyDTO) {
        var company = companyRepository.findById(id);

        if (company.isEmpty()) return ResponseEntity.notFound().build();

        var updatedCompany = company.get();
        updatedCompany.update(companyDTO);
        companyRepository.save(company.get());

        return ResponseEntity.ok(new CompanyDetailsDTO(updatedCompany));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        var company = companyRepository.findById(id);

        if (company.isEmpty()) return ResponseEntity.notFound().build();

        companyRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/books")
    public ResponseEntity<?> getBooksByCompany(@PathVariable Long id) {
        var company = companyRepository.findById(id);

        if (company.isEmpty()) return ResponseEntity.notFound().build();

        var books = company.get().getBooks().stream().map(BookDetailsDTO::new).toList();

        return ResponseEntity.ok(books);
    }
}
