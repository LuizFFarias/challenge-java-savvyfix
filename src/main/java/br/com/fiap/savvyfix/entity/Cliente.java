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
@Table(name = "CLIENTE", uniqueConstraints = {
        @UniqueConstraint(name = "UK_CPF_CLIENTE", columnNames = "CPF_CLIE")
})
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CLIENTE")
    @SequenceGenerator(name = "SQ_CLIENTE", sequenceName = "SQ_CLIENTE", allocationSize = 1)
    @Column(name = "ID_CLIENTE")
    private Long id;

    @Column(name = "NM_CLIE", nullable = false, length = 50)
    private String nome;

    @Column(name = "CPF_CLIE", nullable = false, length = 11)
    private String cpf;

    @Column(name = "SENHA_CLIE", nullable = false, length = 50 )
    private  String senha;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(
            name = "ID_ENDERECO",
            referencedColumnName = "ID_ENDERECO",
            foreignKey = @ForeignKey(
                    name = "ID_ENDERECO_FK"
            )
    )

    private Endereco endereco;
}