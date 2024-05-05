package com.github.ericknathan.books.models;

import com.github.ericknathan.books.dtos.location.ManageLocationDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="TB_LOCALIZACAO")
public class LocationModel {
    @Id
    @GeneratedValue
    @Column(name="id_localizacao")
    private Long id;

    @Column(name="rua", nullable = false, length = 100)
    private String street;

    @Column(name="numero", nullable = false, length = 10)
    private String number;

    @Column(name="cidade", nullable = false, length = 100)
    private String city;

    @Column(name="estado", nullable = false, length = 100)
    private String state;

    @Column(name="cep", nullable = false, length = 8)
    private String zipCode;

    @OneToOne
    @JoinColumn(name="id_editora", nullable = false)
    private CompanyModel company;

    public LocationModel(ManageLocationDTO locationDTO) {
        this.street = locationDTO.street();
        this.number = locationDTO.number();
        this.city = locationDTO.city();
        this.state = locationDTO.state();
        this.zipCode = locationDTO.zipCode();
    }
}
