package br.com.fiap.savvyfix.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CompraRequest (

        @NotNull(message = "O nome do produto é obrigatório")
        String nomeProd,

        @NotNull(message = "A quantidade de produtos é obrigatório")
        Integer qntdProd,

        @NotNull(message = "As especificacoes são obrigatórias")
        String especificacoes,

        @Valid
        AbstractRequest cliente,

        @Valid
        AbstractRequest produto
) {}