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
@Table(name = "CLIENTE")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CLIENTE")
    @SequenceGenerator(name = "SQ_CLIENTE", sequenceName = "SQ_CLIENTE", allocationSize = 1)
    @Column(name = "ID_CLIENTE")
    private Long id;

    @Column(name = "NM_CLIE")
    private String nome;

    @Column(name = "CPF_CLIE", length = 11)
    private String cpf;

    @Column(name = "SENHA_CLIE")
    private  String senha;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(
            name = "CEP_ENDERECO",
            referencedColumnName = "CEP_ENDERECO",
            foreignKey = @ForeignKey(
                    name = "CLIENTE_ENDERECO_FK"
            )
    )

    private Endereco endereco;
}