package br.com.fiap.savvyfix.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ProdutoResponse (

        Long id,
        String nome,
        String descricao,
        String marca,
        float precoFixo
) {}