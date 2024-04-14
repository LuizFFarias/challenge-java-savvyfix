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
@Table(name = "COMPRA")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_COMPRA")
    @SequenceGenerator(name = "SQ_COMPRA", sequenceName = "SQ_COMPRA", allocationSize = 1)
    @Column(name = "ID_COMPRA")
    private Long id;

    private String nomeProd;

    private String qntdProd;

    private float valorCompra;

    private  String especificacoes;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(
            name = "PRODUTO",
            referencedColumnName = "ID_PROD",
            foreignKey = @ForeignKey(
                    name = "COMPRA_PRODUTO_FK"
            )
    )

    private Produto produto;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(
            name = "CLIENTE",
            referencedColumnName = "ID_CLIENTE",
            foreignKey = @ForeignKey(
                    name = "COMPRA_CLIENTE_FK"
            )
    )

    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(
            name = "ATIVIDADES",
            referencedColumnName = "PRECO_VARIADO",
            foreignKey = @ForeignKey(
                    name = "COMPRA_ATIVIDADES_FK"
            )
    )

    private Atividades atividades;
}