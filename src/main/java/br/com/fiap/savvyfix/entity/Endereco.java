package br.com.fiap.savvyfix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


@Entity
@Table(name = "ENDERECO")
public class Endereco {

    @Id
    @Column(name = "CEP_ENDERECO")
    private String cep;

    @Column(name = "RUA_ENDERECO")
    private String rua;

    @Column(name = "NUM_ENDERECO")
    private String numero;

    @Column(name = "BAIRRO_ENDERECO")
    private String bairro;

    @Column(name = "CIDADE_ENDERECO")
    private String cidade;

    @Column(name = "ESTADO_ENDERECO")
    private String estado;

    @Column(name = "PAIS")
    private String pais;

}