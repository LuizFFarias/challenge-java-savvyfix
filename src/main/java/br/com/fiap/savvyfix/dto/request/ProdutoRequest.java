package br.com.fiap.savvyfix.dto.request;

import jakarta.validation.constraints.NotNull;

public record ProdutoRequest (
        @NotNull(message = "O nome é obrigatório")
        String nome,

        @NotNull(message = "A descrição do produto é obrigatório")
        String descricao,

        @NotNull(message = "A marca do produto é obrigatória")
        String marca,

        @NotNull(message = "O preço do produto é obrigatório")
        float precoFixo
) {}