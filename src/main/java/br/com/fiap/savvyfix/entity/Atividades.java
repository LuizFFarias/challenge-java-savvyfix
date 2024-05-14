package br.com.fiap.savvyfix.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "Atividades")
public class Atividades {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ATIVIDADES")
    @SequenceGenerator(name = "SQ_ATIVIDADES", sequenceName = "SQ_ATIVIDADES", allocationSize = 1)
    @Column(name = "ID_ATIVIDADES")
    private Long id;

    @Column(name = "PRECO_VARIADO", nullable = false)
    private Float precoVariado;

    @Column(name = "HORARIO_ATUAL")
    private LocalTime horarioAtual;

    @Column(name = "LOCALIZACAO_ATUAL", length = 50)
    private String localizacaoAtual;

    @Column(name = "CLIMA_ATUAL", length = 20)
    private String climaAtual;

    @Column(name = "QNTD_PROCURA", nullable = false, length = 10)
    private Integer qntdProcura;

    @Column(name = "DEMANDA_PRODUTO", nullable = false, length = 2)
    private String demanda;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(
            name = "ID_CLIENTE",
            referencedColumnName = "ID_CLIENTE",
            foreignKey = @ForeignKey(
                    name = "CLIENTE_ATIVIDADES_FK"
            )
    )
    private Cliente cliente;

}