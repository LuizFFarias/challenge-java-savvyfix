package br.com.fiap.savvyfix.dto.response;

import lombok.Builder;


@Builder
public record ClienteResponse (

        Long id,
        String nome,
        int cpf,
        String senha,

        EnderecoResponse endereco
) {
}