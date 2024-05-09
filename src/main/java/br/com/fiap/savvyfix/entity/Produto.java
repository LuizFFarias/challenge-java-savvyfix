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
@Table(name = "PRODUTO")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PRODUTO")
    @SequenceGenerator(name = "SQ_PRODUTO", sequenceName = "SQ_PRODUTO", allocationSize = 1)
    @Column(name = "ID_PROD")
    private Long id;

    @Column(name = "NM_PROD", nullable = false, length = 50)
    private  String nome;

    @Column(name = "DESC_PROD", nullable = false, length = 50)
    private String descricao;

    @Column(name = "MARCA_PROD", nullable = false, length = 15)
    private String marca;

    @Column(name = "PRECO_FIXO", nullable = false)
    private Float precoFixo;
}