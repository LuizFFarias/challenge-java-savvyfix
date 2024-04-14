package br.com.fiap.savvyfix.dto.request;

import jakarta.validation.constraints.NotNull;

public record CompraRequest (

        @NotNull(message = "O nome do produto é obrigatório")
        String nomeProd,

        @NotNull(message = "A quantidade de produtos é obrigatório")
        String qntdProd,

        @NotNull(message = "O valor é obrigatório")
        float valorCompra,

        @NotNull(message = "As especificacoes são obrigatórias")
        String especificacoes,

        AbstractRequest cliente,

        AbstractRequest produto,

        AtividadesRequest atividades
){
}