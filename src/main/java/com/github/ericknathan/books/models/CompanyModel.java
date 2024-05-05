package com.github.ericknathan.books.models;

import com.github.ericknathan.books.dtos.company.CreateCompanyDTO;
import com.github.ericknathan.books.dtos.company.UpdateCompanyDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="TB_EDITORA")
public class CompanyModel {
    @Id
    @GeneratedValue
    @Column(name="id_editora")
    private Long id;

    @Column(name="nome", nullable = false, length = 100)
    private String name;

    @Column(name="cnpj", nullable = false, length = 14)
    private String cnpj;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "company", orphanRemoval = true)
    private LocationModel location;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<BookModel> books;

    public CompanyModel(CreateCompanyDTO companyDTO) {
        this.name = companyDTO.name();
        this.cnpj = companyDTO.cnpj();
        this.location = new LocationModel(companyDTO.location());
        this.location.setCompany(this);
    }

    public void update(UpdateCompanyDTO companyDTO) {
        if(companyDTO.name() != null) this.name = companyDTO.name();
        if(companyDTO.cnpj() != null) this.cnpj = companyDTO.cnpj();
        if(companyDTO.location() != null) this.location = new LocationModel(companyDTO.location());
    }
}
