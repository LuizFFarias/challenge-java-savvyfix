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
    private String cep;

    private String rua;

    private String numero;

    private String bairro;

    private String cidade;

    private String estado;

    private String pais;

}